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
import co.uniandes.grupo11.vinilos.models.Performer

class FavoritePerformersAdapter(
    private val context: Context,
    private val onPerformerClick: (Performer) -> Unit
) : RecyclerView.Adapter<FavoritePerformersAdapter.PerformerViewHolder>() {
    
    private var performers: List<Performer> = emptyList()

    class PerformerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val performerImage: ImageView = view.findViewById(R.id.performer_image)
        private val performerName: TextView = view.findViewById(R.id.performer_name)

        fun bind(performer: Performer, context: Context, onPerformerClick: (Performer) -> Unit) {
            performerName.text = performer.name
            
            Glide.with(context)
                .load(performer.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(performerImage)
            
            itemView.setOnClickListener {
                onPerformerClick(performer)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_performer, parent, false)
        return PerformerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        holder.bind(performers[position], context, onPerformerClick)
    }

    override fun getItemCount() = performers.size

    fun updatePerformers(newPerformers: List<Performer>) {
        performers = newPerformers
        notifyDataSetChanged()
    }
}

