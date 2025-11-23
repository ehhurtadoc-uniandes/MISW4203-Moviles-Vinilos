package co.uniandes.grupo11.vinilos.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.CollectorAlbum

class CollectorAlbumsAdapter(
    private val context: Context,
    private val onAlbumClick: (CollectorAlbum) -> Unit
) : RecyclerView.Adapter<CollectorAlbumsAdapter.CollectorAlbumViewHolder>() {
    
    private var collectorAlbums: List<CollectorAlbum> = emptyList()

    class CollectorAlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val albumCover: ImageView = view.findViewById(R.id.album_cover)
        private val albumName: TextView = view.findViewById(R.id.album_name)
        private val albumPrice: TextView = view.findViewById(R.id.album_price)
        private val albumStatus: TextView = view.findViewById(R.id.album_status)

        fun bind(collectorAlbum: CollectorAlbum, context: Context, onAlbumClick: (CollectorAlbum) -> Unit) {
            albumPrice.text = "$ ${collectorAlbum.price}"
            
            // Traducir el estado
            val statusText = when(collectorAlbum.status.lowercase()) {
                "active" -> "Activo"
                "inactive" -> "Inactivo"
                else -> collectorAlbum.status
            }
            albumStatus.text = statusText
            
            collectorAlbum.album?.let { album ->
                albumName.text = album.name
                
                Glide.with(context)
                    .load(album.cover)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(albumCover)
                
                itemView.setOnClickListener {
                    onAlbumClick(collectorAlbum)
                }
            } ?: run {
                // Si no hay información del álbum, mostrar valores por defecto
                albumName.text = "Álbum #${collectorAlbum.id}"
                albumCover.setImageResource(R.drawable.ic_launcher_background)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorAlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collector_album, parent, false)
        return CollectorAlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectorAlbumViewHolder, position: Int) {
        holder.bind(collectorAlbums[position], context, onAlbumClick)
    }

    override fun getItemCount() = collectorAlbums.size

    fun updateCollectorAlbums(newCollectorAlbums: List<CollectorAlbum>) {
        collectorAlbums = newCollectorAlbums
        notifyDataSetChanged()
    }
}

