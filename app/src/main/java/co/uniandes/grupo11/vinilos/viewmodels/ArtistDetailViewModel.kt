package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.ArtistDetail
import co.uniandes.grupo11.vinilos.repositories.PerformerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _artist = MutableLiveData<ArtistDetail?>()
    val artist: LiveData<ArtistDetail?> = _artist

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val performerRepository = PerformerRepository(application)

    fun loadArtist(artistId: Int) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val loadedArtist = kotlinx.coroutines.withContext(Dispatchers.IO) {
                    performerRepository.getArtistById(artistId)
                }
                _artist.postValue(loadedArtist)
                _error.postValue(null)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Error desconocido")
                _artist.postValue(null)
                _isLoading.postValue(false)
            }
        }
    }
}

