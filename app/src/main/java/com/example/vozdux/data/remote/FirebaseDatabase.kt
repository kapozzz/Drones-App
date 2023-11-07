package com.example.vozdux.data.remote

import android.util.Log
import com.example.vozdux.constants.EMPTY_ID
import com.example.vozdux.data.util.COUNTER_HELPER_NAME
import com.example.vozdux.data.util.DRONE_HELPER_NAME
import com.example.vozdux.data.util.FirebaseHelper
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.Image
import com.example.vozdux.domain.model.drone.UploadDrone
import com.example.vozdux.domain.model.drone.UriImage
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseDatabase @Inject constructor(
    @Named(DRONE_HELPER_NAME) private val dronesHelper: FirebaseHelper
) {

    private val dronesReference =
        dronesHelper.database ?: throw IllegalStateException("Empty database in firebaseHelper")
    private val storage =
        dronesHelper.storage ?: throw IllegalStateException("Empty storage in firebaseHelper")

    private val scope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null

    private var _dronesStateFlow: MutableStateFlow<List<UploadDrone>> =
        MutableStateFlow(emptyList())
    val dronesStateFlow: StateFlow<List<UploadDrone>> = _dronesStateFlow

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val droneList = mutableListOf<UploadDrone>()
                    for (droneSnapshot in dataSnapshot.children) {
                        droneSnapshot.getValue(UploadDrone::class.java)?.let { uploadDrone ->
                            droneList.add(uploadDrone)
                        }
                    }
                    _dronesStateFlow.value = droneList
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Vozdux", "invalid connection with Firebase Realtime Database")
                }
            }
            dronesReference.addValueEventListener(valueEventListener)
        }
    }

    suspend fun insertDrone(drone: Drone): Boolean = suspendCoroutine { continuation ->
        job?.cancel()
        job = scope.launch {
            val images = async {
                drone.images.mapNotNull {
                    Image(
                        id = it.id,
                        source = it.source,
                        uri = insertImage(it) ?: return@mapNotNull null
                    )
                }
            }
            val tempUploadDrone = UploadDrone.fromDrone(drone).copy(
                id = if (drone.id == EMPTY_ID) dronesHelper.getNewKey() else drone.id,
                images = images.await()
            )
            dronesReference.child(tempUploadDrone.id).setValue(tempUploadDrone)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }
        }
    }

    private suspend fun insertImage(uploadImage: UriImage): String? =
        suspendCoroutine { continuation ->
            val path = storage.child(uploadImage.id)
            uploadImage.uri.let { uri ->
                path.putFile(uri ?: throw IOException("Uri is null"))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            path.downloadUrl.addOnSuccessListener { uri ->
                                continuation.resume(uri.toString())
                            }.addOnFailureListener { exception ->
                                continuation.resume(null)
                            }
                        } else {
                            continuation.resume(uploadImage.uri.toString())
                            /*
                            TODO
                            Хуй знает когда но это ебнится, я в этом уверен на все 100%.
                            Возвращается не null а id потому что по какой-то неясной мне причине,
                             происходит !task.isSuccessful когда изображение с данным id уже есть в storage
                             */
                        }
                    }
            }
        }

}

