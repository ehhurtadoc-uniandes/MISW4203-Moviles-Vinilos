package co.uniandes.grupo11.vinilos.repositories

import android.app.Application
import co.uniandes.grupo11.vinilos.models.Album
import co.uniandes.grupo11.vinilos.models.Collector
import co.uniandes.grupo11.vinilos.models.CollectorAlbum
import co.uniandes.grupo11.vinilos.models.CollectorDetail
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter
import java.util.concurrent.atomic.AtomicInteger

class CollectorRepository(val application: Application) {
    fun refreshData(callback: (List<Collector>) -> Unit, onError: (Exception) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectors(
            onComplete = {
                callback(it)
            },
            onError = onError
        )
    }

    fun getCollectorDetail(collectorId: Int, callback: (CollectorDetail) -> Unit, onError: (Exception) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getCollector(
            collectorId,
            onComplete = { collectorDetail ->
                // Cargar los detalles de los álbumes si hay collectorAlbums
                if (collectorDetail.collectorAlbums.isNotEmpty()) {
                    loadAlbumDetails(collectorDetail, callback, onError)
                } else {
                    callback(collectorDetail)
                }
            },
            onError = onError
        )
    }

    private fun loadAlbumDetails(
        collectorDetail: CollectorDetail,
        callback: (CollectorDetail) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val albums = mutableMapOf<Int, Album>()
        val pending = AtomicInteger(collectorDetail.collectorAlbums.size)
        
        collectorDetail.collectorAlbums.forEach { collectorAlbum ->
            NetworkServiceAdapter.getInstance(application).getAlbum(
                collectorAlbum.id,
                onComplete = { album ->
                    synchronized(albums) {
                        albums[collectorAlbum.id] = album
                    }
                    if (pending.decrementAndGet() == 0) {
                        // Todas las llamadas completadas, actualizar el collectorDetail
                        val updatedCollectorAlbums = collectorDetail.collectorAlbums.map { ca ->
                            ca.copy(album = albums[ca.id])
                        }
                        callback(collectorDetail.copy(collectorAlbums = updatedCollectorAlbums))
                    }
                },
                onError = { error ->
                    // Si falla una llamada, continuar con las demás
                    if (pending.decrementAndGet() == 0) {
                        val updatedCollectorAlbums = collectorDetail.collectorAlbums.map { ca ->
                            ca.copy(album = albums[ca.id])
                        }
                        callback(collectorDetail.copy(collectorAlbums = updatedCollectorAlbums))
                    }
                }
            )
        }
    }
}

