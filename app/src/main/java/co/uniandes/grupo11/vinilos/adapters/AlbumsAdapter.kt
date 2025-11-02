package co.uniandes.grupo11.vinilos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Album

class AlbumsAdapter(
    private val onAlbumClick: (Album) -> Unit
) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {
    
    private var albums: List<Album> = emptyList()

    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val albumCover: ImageView = view.findViewById(R.id.album_cover)
        private val albumName: TextView = view.findViewById(R.id.album_name)
        private val albumArtists: TextView = view.findViewById(R.id.album_artists)
        private val albumGenre: TextView = view.findViewById(R.id.album_genre)

        fun bind(album: Album, onAlbumClick: (Album) -> Unit) {
            albumName.text = album.name
            
            val artistsText = if (album.performers.isNotEmpty()) {
                album.performers.joinToString(", ") { it.name }
            } else {
                "Artista desconocido"
            }
            albumArtists.text = artistsText
            
            albumGenre.text = album.genre
            
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
            .inflate(R.layout.item_album, parent, false)
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

