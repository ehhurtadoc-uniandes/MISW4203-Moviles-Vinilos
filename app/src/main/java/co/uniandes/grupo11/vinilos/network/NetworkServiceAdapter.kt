package co.uniandes.grupo11.vinilos.network

import android.content.Context

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        @Volatile
        private var instance: NetworkServiceAdapter? = null

        fun getInstance(context: Context): NetworkServiceAdapter {
            return instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also { instance = it }
            }
        }
    }

    // Aquí, más adelante, puedes añadir tus llamadas de red con Retrofit.
    // Por ejemplo:
    // suspend fun getAlbums(): List<Album> {
    //     // Lógica para llamar a la API y devolver la lista de álbumes.
    //     return emptyList() // Placeholder
    // }
}
