package co.uniandes.grupo11.vinilos

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import co.uniandes.grupo11.vinilos.adapters.PerformersAdapter
import co.uniandes.grupo11.vinilos.adapters.TracksAdapter
import co.uniandes.grupo11.vinilos.network.NetworkServiceAdapter
import co.uniandes.grupo11.vinilos.utils.PicassoConfig
import java.text.SimpleDateFormat
import java.util.Locale

class AlbumDetailActivity : AppCompatActivity() {
    private lateinit var albumCoverImage: ImageView
    private lateinit var albumTitleText: TextView
    private lateinit var albumGenreText: TextView
    private lateinit var albumDescriptionText: TextView
    private lateinit var recordLabelText: TextView
    private lateinit var releaseDateText: TextView
    private lateinit var performersContainer: LinearLayout
    private lateinit var tracksRecyclerView: RecyclerView
    private lateinit var performersRecyclerView: RecyclerView
    private lateinit var tracksAdapter: TracksAdapter
    private lateinit var performersAdapter: PerformersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        PicassoConfig.initPicasso(this)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        albumCoverImage = findViewById(R.id.album_cover_image)
        albumTitleText = findViewById(R.id.album_title)
        albumGenreText = findViewById(R.id.album_genre)
        albumDescriptionText = findViewById(R.id.album_description)
        recordLabelText = findViewById(R.id.record_label)
        releaseDateText = findViewById(R.id.release_date)
        performersContainer = findViewById(R.id.performers_container)
        tracksRecyclerView = findViewById(R.id.tracks_recycler_view)
        performersRecyclerView = findViewById(R.id.performers_recycler_view)

        tracksAdapter = TracksAdapter()
        performersAdapter = PerformersAdapter(this)

        tracksRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AlbumDetailActivity)
            adapter = tracksAdapter
        }

        performersRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AlbumDetailActivity)
            adapter = performersAdapter
        }

        val albumId = intent.getIntExtra("albumId", -1)
        if (albumId != -1) {
            loadAlbumDetails(albumId)
        } else {
            Toast.makeText(this, "Error: ID del álbum no proporcionado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun loadAlbumDetails(albumId: Int) {
        val networkService = NetworkServiceAdapter.getInstance(this)
        networkService.getAlbum(albumId,
            onComplete = { album ->
                runOnUiThread {
                    albumTitleText.text = album.name
                    albumGenreText.text = album.genre
                    albumDescriptionText.text = album.description
                    recordLabelText.text = album.recordLabel

                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
                    try {
                        val date = inputFormat.parse(album.releaseDate)
                        val formattedDate = date?.let { outputFormat.format(it) }
                        releaseDateText.text = "Fecha de lanzamiento: $formattedDate"
                    } catch (e: Exception) {
                        releaseDateText.text = "Fecha de lanzamiento: ${album.releaseDate}"
                    }

                    Log.d("AlbumDetailActivity", "Intentando cargar imagen del álbum desde: ${album.cover}")
                    Glide.with(this)
                        .load(album.cover)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(albumCoverImage)

                    tracksAdapter.updateTracks(album.tracks)

                    Log.d("AlbumDetailActivity", "Actualizando lista de intérpretes: ${album.performers.size} intérpretes")
                    performersAdapter.updatePerformers(album.performers)

                    performersContainer.removeAllViews()
                    album.performers.forEach { performer ->
                        val performerView = TextView(this).apply {
                            text = performer.name
                            textSize = 14f
                            setPadding(0, 0, 16, 0)
                            setTextColor(getColor(android.R.color.secondary_text_light))
                        }
                        performersContainer.addView(performerView)
                    }
                }
            },
            onError = { error ->
                runOnUiThread {
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}