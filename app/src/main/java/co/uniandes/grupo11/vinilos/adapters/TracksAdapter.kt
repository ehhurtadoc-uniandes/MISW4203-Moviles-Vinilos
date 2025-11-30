package co.uniandes.grupo11.vinilos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Track

class TracksAdapter : RecyclerView.Adapter<TracksAdapter.TrackViewHolder>() {
    private var tracks: List<Track> = emptyList()

    class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.track_name)
        private val durationTextView: TextView = view.findViewById(R.id.track_duration)

        fun bind(track: Track) {
            nameTextView.text = track.name
            durationTextView.text = track.duration
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount() = tracks.size

    fun updateTracks(newTracks: List<Track>) {
        tracks = newTracks
        notifyDataSetChanged()
    }
}
