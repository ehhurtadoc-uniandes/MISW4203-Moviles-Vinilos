package co.uniandes.grupo11.vinilos.repositories

import android.app.Application
import co.uniandes.grupo11.vinilos.models.Performer
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter

class PerformerRepository(val application: Application) {
    fun refreshData(callback: (List<Performer>) -> Unit, onError: (Exception) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getMusicians(
            onComplete = {
                callback(it)
            },
            onError = onError
        )
    }
}

