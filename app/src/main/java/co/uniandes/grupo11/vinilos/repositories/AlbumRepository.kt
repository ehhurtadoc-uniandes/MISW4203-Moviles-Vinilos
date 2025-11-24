package co.uniandes.grupo11.vinilos.repositories

import android.app.Application
import android.util.Log
import co.uniandes.grupo11.vinilos.models.Album
import co.uniandes.grupo11.vinilos.models.Comment
import co.uniandes.grupo11.vinilos.models.Track
import co.uniandes.grupo11.vinilos.network.CacheManager
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    private val cacheManager = CacheManager.getInstance(application.applicationContext)

    suspend fun refreshData(): List<Album> {
        val cachedAlbums = cacheManager.getAlbumsList()
        if (cachedAlbums != null && cachedAlbums.isNotEmpty()) {
            Log.d("AlbumRepository", "Retornando ${cachedAlbums.size} álbumes desde caché")
            return cachedAlbums
        }

        Log.d("AlbumRepository", "Obteniendo álbumes desde la red")
        val albums = NetworkServiceAdapter.getInstance(application).getAlbums()
        cacheManager.setAlbumsList(albums)
        return albums
    }

    suspend fun getAlbumById(albumId: Int): Album {
        val cachedAlbum = cacheManager.getAlbumDetail(albumId)
        if (cachedAlbum != null) {
            Log.d("AlbumRepository", "Retornando álbum $albumId desde caché")
            return cachedAlbum
        }

        Log.d("AlbumRepository", "Obteniendo álbum $albumId desde la red")
        val album = NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
        cacheManager.addAlbumDetail(albumId, album)
        cacheManager.addComments(albumId, album.comments)
        return album
    }

    suspend fun addTrackToAlbum(albumId: Int, trackName: String, trackDuration: String): Track {
        val track = NetworkServiceAdapter.getInstance(application).addTrackToAlbum(
            albumId,
            trackName,
            trackDuration
        )
        cacheManager.invalidateAlbum(albumId)
        Log.d("AlbumRepository", "Caché del álbum $albumId invalidado después de agregar pista")
        return track
    }

    suspend fun addCommentToAlbum(albumId: Int, description: String, rating: Int, collectorId: Int): Comment {
        val comment = NetworkServiceAdapter.getInstance(application).addCommentToAlbum(
            albumId,
            description,
            rating,
            collectorId
        )
        cacheManager.invalidateAlbum(albumId)
        Log.d("AlbumRepository", "Caché del álbum $albumId invalidado después de agregar comentario")
        return comment
    }

    suspend fun forceRefresh(): List<Album> {
        Log.d("AlbumRepository", "Forzando recarga de álbumes desde la red")
        cacheManager.clearAlbumsList()
        return refreshData()
    }
}


