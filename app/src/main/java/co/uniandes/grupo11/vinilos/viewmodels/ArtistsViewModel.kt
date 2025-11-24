package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.Performer
import co.uniandes.grupo11.vinilos.repositories.PerformerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistsViewModel(application: Application) : AndroidViewModel(application) {
    private val _artists = MutableLiveData<List<Performer>>()
    val artists: LiveData<List<Performer>> = _artists

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val performerRepository = PerformerRepository(application)

    init {
        loadArtists()
    }

    fun loadArtists() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val loadedArtists = kotlinx.coroutines.withContext(Dispatchers.IO) {
                    performerRepository.refreshData()
                }
                _artists.postValue(loadedArtists)
                _error.postValue(null)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Error desconocido al cargar artistas")
                _artists.postValue(emptyList())
                _isLoading.postValue(false)
            }
        }
    }
}

