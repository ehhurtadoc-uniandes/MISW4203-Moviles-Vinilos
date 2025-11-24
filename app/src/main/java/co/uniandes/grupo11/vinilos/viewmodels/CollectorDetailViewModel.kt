package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.CollectorDetail
import co.uniandes.grupo11.vinilos.repositories.CollectorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectorDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _collector = MutableLiveData<CollectorDetail?>()
    val collector: LiveData<CollectorDetail?> = _collector

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val collectorRepository = CollectorRepository(application)

    fun loadCollector(collectorId: Int) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val loadedCollector = kotlinx.coroutines.withContext(Dispatchers.IO) {
                    collectorRepository.getCollectorDetail(collectorId)
                }
                _collector.postValue(loadedCollector)
                _error.postValue(null)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Error desconocido")
                _collector.postValue(null)
                _isLoading.postValue(false)
            }
        }
    }
}

