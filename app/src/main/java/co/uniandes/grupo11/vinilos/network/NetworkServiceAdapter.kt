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
import co.uniandes.grupo11.vinilos.models.ArtistDetail
import co.uniandes.grupo11.vinilos.models.BandDetail
import co.uniandes.grupo11.vinilos.models.Collector
import co.uniandes.grupo11.vinilos.models.CollectorDetail
import co.uniandes.grupo11.vinilos.models.Performer
import co.uniandes.grupo11.vinilos.models.Track
import org.json.JSONArray
import org.json.JSONObject

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

    fun getCollectors(onComplete: (resp: List<Collector>) -> Unit, onError: (error: Exception) -> Unit) {
        requestQueue.add(
            JsonArrayRequest(Request.Method.GET, "$BASE_URL/collectors", null,
                { response ->
                    val collectors = parseCollectorArray(response)
                    onComplete(collectors)
                },
                {
                    onError(Exception(it.message))
                })
        )
    }

    private fun parseCollectorArray(jsonArray: JSONArray): List<Collector> {
        val collectors = mutableListOf<Collector>()
        for (i in 0 until jsonArray.length()) {
            val collectorJson = jsonArray.getJSONObject(i)
            collectors.add(gson.fromJson(collectorJson.toString(), Collector::class.java))
        }
        return collectors
    }

    fun getCollector(collectorId: Int, onComplete: (resp: CollectorDetail) -> Unit, onError: (error: Exception) -> Unit) {
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET, "$BASE_URL/collectors/$collectorId", null,
                { response ->
                    val collectorDetail = gson.fromJson(response.toString(), CollectorDetail::class.java)
                    onComplete(collectorDetail)
                },
                {
                    onError(Exception(it.message))
                })
        )
    }

    fun getMusicians(onComplete: (resp: List<Performer>) -> Unit, onError: (error: Exception) -> Unit) {
        requestQueue.add(
            JsonArrayRequest(Request.Method.GET, "$BASE_URL/musicians", null,
                { response ->
                    val musicians = parseMusicianArray(response)
                    onComplete(musicians)
                },
                {
                    onError(Exception(it.message))
                })
        )
    }

    private fun parseMusicianArray(jsonArray: JSONArray): List<Performer> {
        val musicians = mutableListOf<Performer>()
        for (i in 0 until jsonArray.length()) {
            val musicianJson = jsonArray.getJSONObject(i)
            musicians.add(gson.fromJson(musicianJson.toString(), Performer::class.java))
        }
        return musicians
    }

    fun getMusician(musicianId: Int, onComplete: (resp: ArtistDetail) -> Unit, onError: (error: Exception) -> Unit) {
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET, "$BASE_URL/musicians/$musicianId", null,
                { response ->
                    val artistDetail = gson.fromJson(response.toString(), ArtistDetail::class.java)
                    onComplete(artistDetail)
                },
                {
                    onError(Exception(it.message))
                })
        )
    }

    fun getBand(bandId: Int, onComplete: (resp: BandDetail) -> Unit, onError: (error: Exception) -> Unit) {
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET, "$BASE_URL/bands/$bandId", null,
                { response ->
                    val bandDetail = gson.fromJson(response.toString(), BandDetail::class.java)
                    onComplete(bandDetail)
                },
                {
                    onError(Exception(it.message))
                })
        )
    }

    fun addTrackToAlbum(albumId: Int, trackName: String, trackDuration: String, onComplete: (resp: Track) -> Unit, onError: (error: Exception) -> Unit) {
        val requestBody = JSONObject()
        requestBody.put("name", trackName)
        requestBody.put("duration", trackDuration)

        requestQueue.add(
            JsonObjectRequest(Request.Method.POST, "$BASE_URL/albums/$albumId/tracks", requestBody,
                { response ->
                    val track = gson.fromJson(response.toString(), Track::class.java)
                    onComplete(track)
                },
                {
                    onError(Exception(it.message))
                })
        )
    }
}
