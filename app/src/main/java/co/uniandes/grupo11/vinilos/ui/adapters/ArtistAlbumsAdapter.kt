package co.uniandes.grupo11.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Album
import java.text.SimpleDateFormat
import java.util.Locale

class ArtistAlbumsAdapter(
    private val onAlbumClick: (Album) -> Unit
) : RecyclerView.Adapter<ArtistAlbumsAdapter.AlbumViewHolder>() {
    
    private var albums: List<Album> = emptyList()

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val albumCover: ImageView = view.findViewById(R.id.album_cover)
        private val albumName: TextView = view.findViewById(R.id.album_name)
        private val albumGenres: TextView = view.findViewById(R.id.album_genres)
        private val albumReleaseDate: TextView = view.findViewById(R.id.album_release_date)

        fun bind(album: Album, onAlbumClick: (Album) -> Unit) {
            albumName.text = album.name
            
            // Mostrar géneros como tags
            albumGenres.text = album.genre
            
            // Formatear fecha de lanzamiento
            album.releaseDate?.let { releaseDateStr ->
                try {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
                    val date = inputFormat.parse(releaseDateStr)
                    val formattedDate = date?.let { outputFormat.format(it) }
                    albumReleaseDate.text = formattedDate ?: releaseDateStr
                } catch (e: Exception) {
                    albumReleaseDate.text = releaseDateStr
                }
            } ?: run {
                albumReleaseDate.text = ""
            }
            
            Glide.with(itemView.context)
                .load(album.cover)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(albumCover)
            
            itemView.setOnClickListener {
                onAlbumClick(album)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artist_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albums[position], onAlbumClick)
    }

    override fun getItemCount() = albums.size

    fun updateAlbums(newAlbums: List<Album>) {
        albums = newAlbums
        notifyDataSetChanged()
    }
}

