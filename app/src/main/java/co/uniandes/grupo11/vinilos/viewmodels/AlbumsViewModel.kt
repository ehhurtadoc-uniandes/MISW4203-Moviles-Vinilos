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

class AlbumsViewModel(application: Application) : AndroidViewModel(application) {
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val albumRepository = AlbumRepository(application)

    init {
        loadAlbums()
    }

    fun loadAlbums() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            albumRepository.refreshData(
                callback = { loadedAlbums ->
                    _albums.postValue(loadedAlbums)
                    _error.postValue(null)
                    _isLoading.postValue(false)
                },
                onError = { throwable ->
                    _error.postValue(throwable.message ?: "Error desconocido al cargar álbumes")
                    _albums.postValue(emptyList())
                    _isLoading.postValue(false)
                }
            )
        }
    }
}

