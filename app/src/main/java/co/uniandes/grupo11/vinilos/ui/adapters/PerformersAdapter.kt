package co.uniandes.grupo11.vinilos.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Performer
import java.text.SimpleDateFormat
import java.util.Locale

class PerformersAdapter(private val context: android.content.Context) : RecyclerView.Adapter<PerformersAdapter.PerformerViewHolder>() {
    private var performers: List<Performer> = emptyList()


    class PerformerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.performer_image)
        private val nameTextView: TextView = view.findViewById(R.id.performer_name)
        private val descriptionTextView: TextView = view.findViewById(R.id.performer_description)
        private val birthDateTextView: TextView = view.findViewById(R.id.performer_birth_date)

        fun bind(performer: Performer) {
            nameTextView.text = performer.name
            descriptionTextView.text = performer.description

            Log.d("PerformersAdapter", "Intentando cargar imagen desde: ${performer.image}")
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide.with(itemView.context)
                .load(performer.image)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView)

            performer.birthDate?.let { birthDateStr ->
                try {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
                    val date = inputFormat.parse(birthDateStr)
                    val formattedDate = date?.let { outputFormat.format(it) }
                    birthDateTextView.text = itemView.context.getString(R.string.birth_date_label) + ": " + formattedDate
                } catch (e: Exception) {
                    birthDateTextView.text = itemView.context.getString(R.string.birth_date_label) + ": " + birthDateStr
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_performer, parent, false)
        return PerformerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PerformerViewHolder, position: Int) {
        holder.bind(performers[position])
    }

    override fun getItemCount() = performers.size

    fun updatePerformers(newPerformers: List<Performer>) {
        performers = newPerformers
        notifyDataSetChanged()
    }
}


