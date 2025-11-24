package co.uniandes.grupo11.vinilos.repositories

import android.app.Application
import android.util.Log
import co.uniandes.grupo11.vinilos.models.ArtistDetail
import co.uniandes.grupo11.vinilos.models.BandDetail
import co.uniandes.grupo11.vinilos.models.Performer
import co.uniandes.grupo11.vinilos.network.CacheManager
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter

class PerformerRepository(val application: Application) {
    private val cacheManager = CacheManager.getInstance(application.applicationContext)

    suspend fun refreshData(): List<Performer> {
        val cachedArtists = cacheManager.getArtistsList()
        if (cachedArtists != null && cachedArtists.isNotEmpty()) {
            Log.d("PerformerRepository", "Retornando ${cachedArtists.size} artistas desde caché")
            return cachedArtists
        }

        Log.d("PerformerRepository", "Obteniendo artistas desde la red")
        val performers = NetworkServiceAdapter.getInstance(application).getMusicians()
        cacheManager.setArtistsList(performers)
        return performers
    }

    suspend fun getArtistById(artistId: Int): ArtistDetail {
        val cachedArtist = cacheManager.getArtistDetail(artistId)
        if (cachedArtist != null && cachedArtist is ArtistDetail) {
            Log.d("PerformerRepository", "Retornando artista $artistId desde caché")
            return cachedArtist
        }

        Log.d("PerformerRepository", "Obteniendo artista $artistId desde la red")
        val artist = NetworkServiceAdapter.getInstance(application).getMusician(artistId)
        cacheManager.addArtistDetail(artistId, artist)
        return artist
    }

    suspend fun getBandById(bandId: Int): BandDetail {
        val cachedBand = cacheManager.getArtistDetail(bandId)
        if (cachedBand != null && cachedBand is BandDetail) {
            Log.d("PerformerRepository", "Retornando banda $bandId desde caché")
            return cachedBand
        }

        Log.d("PerformerRepository", "Obteniendo banda $bandId desde la red")
        val band = NetworkServiceAdapter.getInstance(application).getBand(bandId)
        cacheManager.addArtistDetail(bandId, band)
        return band
    }

    suspend fun getPerformerDetail(performer: Performer): Any {
        return if (performer.birthDate != null) {
            getArtistById(performer.id)
        } else if (performer.creationDate != null) {
            getBandById(performer.id)
        } else {
            throw Exception("No se puede determinar el tipo de performer")
        }
    }

    suspend fun forceRefresh(): List<Performer> {
        Log.d("PerformerRepository", "Forzando recarga de artistas desde la red")
        cacheManager.clearArtistsList()
        return refreshData()
    }
}

