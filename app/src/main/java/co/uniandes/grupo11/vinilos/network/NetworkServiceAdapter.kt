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
import co.uniandes.grupo11.vinilos.models.Comment
import co.uniandes.grupo11.vinilos.models.Performer
import co.uniandes.grupo11.vinilos.models.Track
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getAlbums() = suspendCoroutine<List<Album>> { cont ->
        requestQueue.add(
            JsonArrayRequest(Request.Method.GET, "$BASE_URL/albums", null,
                { response ->
                    val albums = parseAlbumArray(response)
                    cont.resume(albums)
                },
                {
                    cont.resumeWithException(Exception(it.message))
                })
        )
    }

    suspend fun getAlbum(albumId: Int) = suspendCoroutine<Album> { cont ->
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET, "$BASE_URL/albums/$albumId", null,
                { response ->
                    val album = gson.fromJson(response.toString(), Album::class.java)
                    cont.resume(album)
                },
                {
                    cont.resumeWithException(Exception(it.message))
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

    suspend fun getCollectors() = suspendCoroutine<List<Collector>> { cont ->
        requestQueue.add(
            JsonArrayRequest(Request.Method.GET, "$BASE_URL/collectors", null,
                { response ->
                    val collectors = parseCollectorArray(response)
                    cont.resume(collectors)
                },
                {
                    cont.resumeWithException(Exception(it.message))
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

    suspend fun getCollector(collectorId: Int) = suspendCoroutine<CollectorDetail> { cont ->
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET, "$BASE_URL/collectors/$collectorId", null,
                { response ->
                    val collectorDetail = gson.fromJson(response.toString(), CollectorDetail::class.java)
                    cont.resume(collectorDetail)
                },
                {
                    cont.resumeWithException(Exception(it.message))
                })
        )
    }

    suspend fun getMusicians() = suspendCoroutine<List<Performer>> { cont ->
        requestQueue.add(
            JsonArrayRequest(Request.Method.GET, "$BASE_URL/musicians", null,
                { response ->
                    val musicians = parseMusicianArray(response)
                    cont.resume(musicians)
                },
                {
                    cont.resumeWithException(Exception(it.message))
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

    suspend fun getMusician(musicianId: Int) = suspendCoroutine<ArtistDetail> { cont ->
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET, "$BASE_URL/musicians/$musicianId", null,
                { response ->
                    val artistDetail = gson.fromJson(response.toString(), ArtistDetail::class.java)
                    cont.resume(artistDetail)
                },
                {
                    cont.resumeWithException(Exception(it.message))
                })
        )
    }

    suspend fun getBand(bandId: Int) = suspendCoroutine<BandDetail> { cont ->
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET, "$BASE_URL/bands/$bandId", null,
                { response ->
                    val bandDetail = gson.fromJson(response.toString(), BandDetail::class.java)
                    cont.resume(bandDetail)
                },
                {
                    cont.resumeWithException(Exception(it.message))
                })
        )
    }

    suspend fun addTrackToAlbum(albumId: Int, trackName: String, trackDuration: String) = suspendCoroutine<Track> { cont ->
        val requestBody = JSONObject()
        requestBody.put("name", trackName)
        requestBody.put("duration", trackDuration)

        requestQueue.add(
            JsonObjectRequest(Request.Method.POST, "$BASE_URL/albums/$albumId/tracks", requestBody,
                { response ->
                    val track = gson.fromJson(response.toString(), Track::class.java)
                    cont.resume(track)
                },
                {
                    cont.resumeWithException(Exception(it.message))
                })
        )
    }

    suspend fun addCommentToAlbum(albumId: Int, description: String, rating: Int, collectorId: Int) = suspendCoroutine<Comment> { cont ->
        val requestBody = JSONObject()
        requestBody.put("description", description)
        requestBody.put("rating", rating)
        
        val collectorObj = JSONObject()
        collectorObj.put("id", collectorId)
        requestBody.put("collector", collectorObj)

        requestQueue.add(
            JsonObjectRequest(Request.Method.POST, "$BASE_URL/albums/$albumId/comments", requestBody,
                { response ->
                    val comment = gson.fromJson(response.toString(), Comment::class.java)
                    cont.resume(comment)
                },
                {
                    cont.resumeWithException(Exception(it.message))
                })
        )
    }
}
