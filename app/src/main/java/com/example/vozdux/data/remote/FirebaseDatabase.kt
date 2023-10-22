package com.example.vozdux.data.remote

import android.net.Uri
import android.util.Log
import com.example.vozdux.constants.EMPTY_ID
import com.example.vozdux.data.local.util.DataStoreManager
import com.example.vozdux.data.util.COUNTER_HELPER_NAME
import com.example.vozdux.data.util.DRONE_HELPER_NAME
import com.example.vozdux.data.util.FirebaseHelper
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.Image
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseDatabase @Inject constructor(
    @Named(DRONE_HELPER_NAME) private val dronesHelper: FirebaseHelper,
    @Named(COUNTER_HELPER_NAME) private val counterHelper: FirebaseHelper,
    private val dataStoreManager: DataStoreManager
) {

    private val dronesReference =
        dronesHelper.database ?: throw IllegalStateException("Empty database in firebaseHelper")
    private val storage =
        dronesHelper.storage ?: throw IllegalStateException("Empty storage in firebaseHelper")
    private val counterReference =
        counterHelper.database ?: throw IllegalStateException("Empty database in firebaseHelper")

    private val scope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null

    private var _dronesStateFlow: MutableStateFlow<List<Drone>> = MutableStateFlow(emptyList())
    private var _imagesStateFlow: MutableStateFlow<MutableList<Pair<String, Uri>>> =
        MutableStateFlow(
            mutableListOf()
        )

    private var _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val dronesStateFlow: StateFlow<List<Drone>> = _dronesStateFlow
    val imagesStateFlow: StateFlow<List<Pair<String, Uri>>> = _imagesStateFlow
    val loadingState: StateFlow<Boolean> = _loadingState

    init {
        job?.cancel()
        job = scope.launch {
            val remoteCounter = getCounter()
            val localValue = dataStoreManager.getCounter().first()
            if (remoteCounter != localValue) {
                try {
                    refreshDrones()
                    refreshImages()
                    dataStoreManager.setCounter(remoteCounter)
                } catch (e: Exception) {
                    return@launch
                }
            }
        }
    }

    private fun refreshDrones() {
        dronesReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _loadingState.value = false
                job?.cancel()
                job = scope.launch {
                    val droneList = mutableListOf<Drone>()
                    for (droneSnapshot in dataSnapshot.children) {
                        val drone = droneSnapshot.getValue(Drone::class.java)
                        if (drone != null) {
                            droneList.add(drone)
                        }
                    }
                    _dronesStateFlow.value = droneList
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                throw Exception("invalid connection with firebase database")
            }
        })
    }

    suspend fun insertDrone(drone: Drone): Boolean = suspendCoroutine { continuation ->
        val tempDrone = drone.copy(
            id = if (drone.id == EMPTY_ID) dronesHelper.getNewKey() else drone.id
        )
        dronesReference.child(tempDrone.id).setValue(tempDrone).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(true)
                job?.cancel()
                job = scope.launch {
                    incrementCounter()
                }
            } else {
                continuation.resume(false)
            }
        }
    }

    suspend fun insertImage(image: Image): Boolean = suspendCoroutine { continuation ->
        val path = storage.child(image.id)
        image.uri?.let { uri ->
            path.putFile(uri).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                    job?.cancel()
                    job = scope.launch {
                        incrementCounter()
                    }
                } else {
                    continuation.resume(false)
                }
            }
        }
    }

    private suspend fun refreshImages() {
        storage.listAll().addOnSuccessListener { listResult ->
            val imageList = mutableListOf<Pair<String, Uri>>()
            val totalImages = listResult.items.size
            var downloadedImages = 0
            for (item in listResult.items) {
                val imageName = item.name
                item.downloadUrl.addOnSuccessListener { uri ->
                    val imageUri = uri.toString()
                    imageList.add(Pair(imageName, Uri.parse(imageUri)))
                    downloadedImages++
                    if (downloadedImages == totalImages) {
                        _imagesStateFlow.value = imageList
                        _loadingState.value = true
                        Log.d("DEB", "IMAGES LOADING COMPLETE")
                        Log.d("DEB", "IMAGES: ${_imagesStateFlow.value}")
                    }
                }.addOnFailureListener {
                    throw Exception("invalid connection with firebase storage")
                }
            }
        }.addOnFailureListener {
            Log.e("FIREBASE", "CONNECTION WITH STORAGE ERROR")
            throw Exception("invalid connection with firebase storage")
        }
    }

    private suspend fun getCounter(): Long  = suspendCoroutine { continuation ->
        counterReference.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result.value?.let { it as Long } ?: 0)
            } else {
                continuation.resume(0)
            }
        }
    }

    private suspend fun incrementCounter() {
        counterReference.setValue(getCounter() + 1)
    }
}