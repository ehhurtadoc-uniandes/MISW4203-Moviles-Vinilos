package co.uniandes.grupo11.vinilos.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import co.uniandes.grupo11.vinilos.BuildConfig
import co.uniandes.grupo11.vinilos.models.Album
import org.json.JSONArray

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        val BASE_URL = BuildConfig.BASE_URL
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    private val gson = Gson()

    fun getAlbums(onComplete: (resp: List<Album>) -> Unit, onError: (error: Exception) -> Unit) {
        requestQueue.add(
            JsonArrayRequest(Request.Method.GET, "$BASE_URL/albums", null,
                { response ->
                    val albums = parseAlbumArray(response)
                    onComplete(albums)
                },
                {
                    onError(Exception(it.message))
                })
        )
    }

    fun getAlbum(albumId: Int, onComplete: (resp: Album) -> Unit, onError: (error: Exception) -> Unit) {
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET, "$BASE_URL/albums/$albumId", null,
                { response ->
                    val album = gson.fromJson(response.toString(), Album::class.java)
                    onComplete(album)
                },
                {
                    onError(Exception(it.message))
                })
        )
    }

    private fun parseAlbumArray(jsonArray: JSONArray): List<Album> {
        val albums = mutableListOf<Album>()
        for (i in 0 until jsonArray.length()) {
            val albumJson = jsonArray.getJSONObject(i)
            albums.add(gson.fromJson(albumJson.toString(), Album::class.java))
        }
        return albums
    }
}
