package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.Track
import co.uniandes.grupo11.vinilos.repositories.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTrackViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _success = MutableLiveData<Track?>()
    val success: LiveData<Track?> = _success

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val albumRepository = AlbumRepository(application)

    fun addTrack(albumId: Int, trackName: String, trackDuration: String) {
        if (trackName.isBlank()) {
            _error.postValue("El nombre de la pista es requerido")
            return
        }

        if (trackDuration.isBlank()) {
            _error.postValue("La duración es requerida")
            return
        }

        if (!isValidDuration(trackDuration)) {
            _error.postValue("Formato de duración inválido. Use MM:SS")
            return
        }

        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val track = kotlinx.coroutines.withContext(Dispatchers.IO) {
                    albumRepository.addTrackToAlbum(albumId, trackName, trackDuration)
                }
                _success.postValue(track)
                _error.postValue(null)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Error al agregar la pista")
                _success.postValue(null)
                _isLoading.postValue(false)
            }
        }
    }

    private fun isValidDuration(duration: String): Boolean {
        val regex = Regex("^[0-9]{1,2}:[0-5][0-9]$")
        return regex.matches(duration)
    }
}

