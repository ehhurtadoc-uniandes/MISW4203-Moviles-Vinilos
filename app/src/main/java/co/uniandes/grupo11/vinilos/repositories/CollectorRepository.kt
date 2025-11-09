package co.uniandes.grupo11.vinilos.repositories

import android.app.Application
import co.uniandes.grupo11.vinilos.models.Collector
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter

class CollectorRepository(val application: Application) {
    fun refreshData(callback: (List<Collector>) -> Unit, onError: (Exception) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getCollectors(
            onComplete = {
                callback(it)
            },
            onError = onError
        )
    }
}

