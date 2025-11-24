package co.uniandes.grupo11.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import co.uniandes.grupo11.vinilos.models.BandDetail
import co.uniandes.grupo11.vinilos.repositories.PerformerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BandDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _band = MutableLiveData<BandDetail?>()
    val band: LiveData<BandDetail?> = _band

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val performerRepository = PerformerRepository(application)

    fun loadBand(bandId: Int) {
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val loadedBand = kotlinx.coroutines.withContext(Dispatchers.IO) {
                    performerRepository.getBandById(bandId)
                }
                _band.postValue(loadedBand)
                _error.postValue(null)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Error desconocido")
                _band.postValue(null)
                _isLoading.postValue(false)
            }
        }
    }
}

