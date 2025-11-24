package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.uniandes.grupo11.vinilos.models.Collector
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter

class AddCommentViewModel(application: Application) : AndroidViewModel(application) {
    
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
        NetworkServiceAdapter.getInstance(getApplication()).getCollectors(
            onComplete = { collectors ->
                _isLoading.value = false
                _collectors.value = collectors
            },
            onError = { exception ->
                _isLoading.value = false
                _error.value = exception.message ?: "Error al cargar coleccionistas"
            }
        )
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
        NetworkServiceAdapter.getInstance(getApplication()).addCommentToAlbum(
            albumId = albumId,
            description = description,
            rating = rating,
            collectorId = collectorId,
            onComplete = { 
                _isLoading.value = false
                _success.value = true
            },
            onError = { exception ->
                _isLoading.value = false
                _error.value = exception.message ?: "Error al agregar comentario"
            }
        )
    }

    fun clearError() {
        _error.value = null
    }

    fun clearSuccess() {
        _success.value = null
    }
}

