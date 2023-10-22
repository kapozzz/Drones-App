package com.example.vozdux.data

import android.util.Log
import com.example.vozdux.data.local.DronesDatabase
import com.example.vozdux.data.local.util.ImagesDirectoryHelper
import com.example.vozdux.data.remote.FirebaseDatabase
import com.example.vozdux.data.util.FirebaseImageLoader
import com.example.vozdux.domain.Repository
import com.example.vozdux.domain.model.drone.Drone
import com.example.vozdux.domain.model.drone.DroneWithImages
import com.example.vozdux.domain.model.drone.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val remoteDatabase: FirebaseDatabase,
    private val localDatabase: DronesDatabase,
    private val directoryHelper: ImagesDirectoryHelper,
    private val imageLoader: FirebaseImageLoader
) : Repository {

    private val scope = CoroutineScope(Dispatchers.Default)
    private var job: Job? = null

    init {
        // wifi condition
        // need application scope
        CoroutineScope(Dispatchers.IO).launch {
            remoteDatabase.loadingState.collect() { loadingIsComplete ->
                if (loadingIsComplete) {
                    job?.cancel()
                    job = scope.launch {
                        remoteDatabase.imagesStateFlow.value.map { pair ->
                            val bitmap = imageLoader.invoke(pair.second.toString())
                            directoryHelper.saveImageToInternalStorage(
                                pair.first,
                                bitmap
                            )
                        }
                        localDatabase.dao().insertListOfDrones(
                            remoteDatabase.dronesStateFlow.value
                        )
                    }
                }
            }
        }
    }

    override suspend fun getDrones(): Flow<List<DroneWithImages>> {
        return localDatabase.dao().getAllDrones().map { drones ->
            if (drones.isEmpty()) emptyList()
            else {
                val images = directoryHelper.loadImagesFromInternalStorage()
                drones.map { drone ->
                    DroneWithImages(
                        drone = drone,
                        images = drone.imageIDs.mapNotNull { imageSourceId ->
                            images.find { image -> image.id == imageSourceId.id }
                        }
                    )
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getDroneById(droneId: String): DroneWithImages? {
        return withContext(Dispatchers.IO) {
            val drone = localDatabase.dao().getDroneById(droneId) ?: return@withContext null
            val _images = directoryHelper.loadImagesFromInternalStorage()
            val images = drone.imageIDs.mapNotNull { imageSourceId ->
                _images.find { image -> image.id == imageSourceId.id }
            }
            return@withContext DroneWithImages(
                drone = drone,
                images = images
            )
        }
    }

    override suspend fun insertDrone(drone: Drone): Boolean {
        return remoteDatabase.insertDrone(drone)
    }

    override suspend fun insertImage(image: Image): Boolean {
        return remoteDatabase.insertImage(image)
    }
}