package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.Collector
import co.uniandes.grupo11.vinilos.repositories.CollectorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectorsViewModel(application: Application) : AndroidViewModel(application) {
    private val _collectors = MutableLiveData<List<Collector>>()
    val collectors: LiveData<List<Collector>> = _collectors

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val collectorRepository = CollectorRepository(application)

    init {
        loadCollectors()
    }

    fun loadCollectors() {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            collectorRepository.refreshData(
                callback = { loadedCollectors ->
                    _collectors.postValue(loadedCollectors)
                    _error.postValue(null)
                    _isLoading.postValue(false)
                },
                onError = { throwable ->
                    _error.postValue(throwable.message ?: "Error desconocido al cargar coleccionistas")
                    _collectors.postValue(emptyList())
                    _isLoading.postValue(false)
                }
            )
        }
    }
}

