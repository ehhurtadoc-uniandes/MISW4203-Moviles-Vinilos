package co.uniandes.grupo11.vinilos.utils

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.*
import java.util.concurrent.TimeUnit

object PicassoConfig {
    private var initialized = false

    fun initPicasso(context: Context) {
        if (initialized) return

        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()

        try {
            val picasso = Picasso.Builder(context)
                .downloader(OkHttp3Downloader(client))
                .build()

            try {
                Picasso.setSingletonInstance(picasso)
            } catch (e: IllegalStateException) {
                // Si ya existe una instancia, la reemplazamos
                val field = Picasso::class.java.getDeclaredField("singleton")
                field.isAccessible = true
                field.set(null, picasso)
            }

            initialized = true
            android.util.Log.d("PicassoConfig", "Picasso inicializado")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
