package co.uniandes.grupo11.vinilos.repositories

import android.app.Application
import android.util.Log
import co.uniandes.grupo11.vinilos.models.Album
import co.uniandes.grupo11.vinilos.models.Collector
import co.uniandes.grupo11.vinilos.models.CollectorDetail
import co.uniandes.grupo11.vinilos.network.CacheManager
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class CollectorRepository(val application: Application) {
    private val cacheManager = CacheManager.getInstance(application.applicationContext)

    suspend fun refreshData(): List<Collector> {
        return NetworkServiceAdapter.getInstance(application).getCollectors()
    }

    suspend fun getCollectorDetail(collectorId: Int): CollectorDetail {
        val cachedCollector = cacheManager.getCollectorDetail(collectorId)
        if (cachedCollector != null) {
            Log.d("CollectorRepository", "Retornando coleccionista $collectorId desde caché")
            return cachedCollector
        }

        Log.d("CollectorRepository", "Obteniendo coleccionista $collectorId desde la red")
        val collectorDetail = NetworkServiceAdapter.getInstance(application).getCollector(collectorId)
        
        return if (collectorDetail.collectorAlbums.isNotEmpty()) {
            loadAlbumDetails(collectorDetail)
        } else {
            cacheManager.addCollectorDetail(collectorId, collectorDetail)
            collectorDetail
        }
    }

    private suspend fun loadAlbumDetails(collectorDetail: CollectorDetail): CollectorDetail = coroutineScope {
        val albums = mutableMapOf<Int, Album>()
        
        collectorDetail.collectorAlbums.map { collectorAlbum ->
            async {
                try {
                    val album = NetworkServiceAdapter.getInstance(application).getAlbum(collectorAlbum.id)
                    synchronized(albums) {
                        albums[collectorAlbum.id] = album
                    }
                } catch (e: Exception) {
                    Log.e("CollectorRepository", "Error loading album ${collectorAlbum.id}: ${e.message}")
                }
            }
        }.awaitAll()
        
        val updatedCollectorAlbums = collectorDetail.collectorAlbums.map { ca ->
            ca.copy(album = albums[ca.id])
        }
        val updatedCollectorDetail = collectorDetail.copy(collectorAlbums = updatedCollectorAlbums)
        cacheManager.addCollectorDetail(collectorDetail.id, updatedCollectorDetail)
        updatedCollectorDetail
    }
}

