package co.uniandes.grupo11.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Performer

class ArtistsAdapter : RecyclerView.Adapter<ArtistsAdapter.ArtistViewHolder>() {
    
    private var artists: List<Performer> = emptyList()

    class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val artistImage: ImageView = view.findViewById(R.id.artist_image)
        private val artistName: TextView = view.findViewById(R.id.artist_name)
        private val artistDescription: TextView = view.findViewById(R.id.artist_description)

        fun bind(artist: Performer) {
            artistName.text = artist.name
            artistDescription.text = artist.description

            Glide.with(itemView.context)
                .load(artist.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(artistImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    override fun getItemCount() = artists.size

    fun updateArtists(newArtists: List<Performer>) {
        artists = newArtists
        notifyDataSetChanged()
    }
}

