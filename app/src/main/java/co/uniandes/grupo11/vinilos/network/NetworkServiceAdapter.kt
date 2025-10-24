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

}
