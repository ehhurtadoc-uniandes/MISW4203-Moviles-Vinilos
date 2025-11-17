package co.uniandes.grupo11.vinilos.repositories

import android.app.Application
import co.uniandes.grupo11.vinilos.models.ArtistDetail
import co.uniandes.grupo11.vinilos.models.BandDetail
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

    fun getArtistById(artistId: Int, callback: (ArtistDetail) -> Unit, onError: (Exception) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getMusician(
            musicianId = artistId,
            onComplete = {
                callback(it)
            },
            onError = onError
        )
    }

    fun getBandById(bandId: Int, callback: (BandDetail) -> Unit, onError: (Exception) -> Unit) {
        NetworkServiceAdapter.getInstance(application).getBand(
            bandId = bandId,
            onComplete = {
                callback(it)
            },
            onError = onError
        )
    }

    fun getPerformerDetail(performer: Performer, callback: (Any) -> Unit, onError: (Exception) -> Unit) {
        if (performer.birthDate != null) {
            // Es un músico
            getArtistById(performer.id, 
                callback = { callback(it) },
                onError = onError
            )
        } else if (performer.creationDate != null) {
            // Es una banda
            getBandById(performer.id,
                callback = { callback(it) },
                onError = onError
            )
        } else {
            onError(Exception("No se puede determinar el tipo de performer"))
        }
    }
}

