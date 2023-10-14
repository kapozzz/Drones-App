package com.example.vozdux.data.remote

import android.net.Uri
import android.util.Log
import com.example.vozdux.constants.EMPTY_ID
import com.example.vozdux.data.util.FirebaseHelper
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.Image
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FirebaseDatabase @Inject constructor(
    private val dronesHelper: FirebaseHelper
) {

    private val database = dronesHelper.database
    private val storage = dronesHelper.storage

    private val scope = CoroutineScope(Dispatchers.IO)
    private var job: Job? = null

    private var _dronesStateFlow: MutableStateFlow<List<Drone>> = MutableStateFlow(emptyList())
    private var _imagesStateFlow: MutableStateFlow<MutableList<Pair<String, Uri>>> = MutableStateFlow(
        mutableListOf()
    )

    private var _loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val dronesStateFlow: StateFlow<List<Drone>> = _dronesStateFlow
    val imagesStateFlow: StateFlow<List<Pair<String, Uri>>> = _imagesStateFlow
    val loadingState: StateFlow<Boolean> = _loadingState

    init {
            val postListener = object : ValueEventListener {
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
                        Log.d("DEB", "DRONES LOADING COMPLETE")
                        Log.d("DEB", "DRONES: ${_dronesStateFlow.value}")
                        refreshImages()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("FIREBASE", "CONNECTION WITH DATABASE ERROR")
                    throw Exception("invalid connection with firebase database")
                }
            }
            database.addValueEventListener(postListener)
    }

    suspend fun insertDrone(drone: Drone): Boolean = suspendCoroutine { continuation ->
        val tempDrone = drone.copy(
            id = if (drone.id == EMPTY_ID) dronesHelper.getNewKey() else drone.id
        )
        database.child(tempDrone.id).setValue(tempDrone)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }
    }

    suspend fun insertImage(image: Image): Boolean =
        suspendCoroutine { continuation ->
            val path = dronesHelper.storage.child(image.id)
            image.uri?.let { uri ->
                path.putFile(uri).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }
            }
        }

    private suspend fun refreshImages() {
        storage.listAll()
            .addOnSuccessListener { listResult ->
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
            }
            .addOnFailureListener {
                Log.e("FIREBASE", "CONNECTION WITH STORAGE ERROR")
                throw Exception("invalid connection with firebase storage")
            }
    }
}