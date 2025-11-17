package co.uniandes.grupo11.vinilos.repositories

import android.app.Application
import co.uniandes.grupo11.vinilos.models.Album
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter

class AlbumRepository(val application: Application) {
    fun refreshData(callback: (List<Album>) -> Unit, onError: (Exception) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbums(
            onComplete = {
                callback(it)
            },
            onError = onError
        )
    }

    fun getAlbumById(albumId: Int, callback: (Album) -> Unit, onError: (Exception) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getAlbum(
            albumId = albumId,
            onComplete = {
                callback(it)
            },
            onError = onError
        )
    }
}


