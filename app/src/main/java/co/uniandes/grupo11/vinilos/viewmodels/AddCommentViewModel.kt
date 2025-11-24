package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.Collector
import co.uniandes.grupo11.vinilos.repositories.AlbumRepository
import co.uniandes.grupo11.vinilos.repositories.CollectorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCommentViewModel(application: Application) : AndroidViewModel(application) {
    
    private val albumRepository = AlbumRepository(application)
    private val collectorRepository = CollectorRepository(application)
    
    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>>
        get() = _collectors

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _success = MutableLiveData<Boolean?>()
    val success: LiveData<Boolean?>
        get() = _success

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?>
        get() = _error

    init {
        loadCollectors()
    }

    private fun loadCollectors() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val collectors = kotlinx.coroutines.withContext(Dispatchers.IO) {
                    collectorRepository.refreshData()
                }
                _collectors.postValue(collectors)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _isLoading.postValue(false)
                _error.postValue(e.message ?: "Error al cargar coleccionistas")
            }
        }
    }

    fun addComment(albumId: Int, description: String, rating: Int, collectorId: Int) {
        if (description.isBlank()) {
            _error.value = "El comentario no puede estar vacío"
            return
        }

        if (collectorId <= 0) {
            _error.value = "Debes seleccionar un coleccionista"
            return
        }

        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Default) {
            try {
                kotlinx.coroutines.withContext(Dispatchers.IO) {
                    albumRepository.addCommentToAlbum(albumId, description, rating, collectorId)
                }
                _success.postValue(true)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _isLoading.postValue(false)
                _error.postValue(e.message ?: "Error al agregar comentario")
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearSuccess() {
        _success.value = null
    }
}

