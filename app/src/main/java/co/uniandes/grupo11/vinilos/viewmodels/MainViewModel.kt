package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.Album
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel simple para MainActivity que puede cargar un álbum y exponerlo mediante LiveData
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _album = MutableLiveData<Album?>()
    val album: LiveData<Album?> = _album

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val networkService by lazy { NetworkServiceAdapter.getInstance(getApplication()) }

    fun loadAlbum(albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            networkService.getAlbum(albumId,
                onComplete = { loadedAlbum ->
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

