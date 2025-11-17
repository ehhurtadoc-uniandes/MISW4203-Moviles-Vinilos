package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.Album
import co.uniandes.grupo11.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _album = MutableLiveData<Album?>()
    val album: LiveData<Album?> = _album

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val albumRepository = AlbumRepository(application)

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            albumRepository.getAlbumById(
                albumId = albumId,
                callback = { loadedAlbum ->
                    _album.postValue(loadedAlbum)
                    _error.postValue(null)
                },
                onError = { throwable ->
                    _error.postValue(throwable.message ?: "Error desconocido")
                    _album.postValue(null)
                }
            )
        }
    }
}

