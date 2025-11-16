package co.uniandes.grupo11.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Award
import java.text.SimpleDateFormat
import java.util.Locale

class AwardsAdapter : RecyclerView.Adapter<AwardsAdapter.AwardViewHolder>() {
    
    private var awards: List<Award> = emptyList()

    class AwardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val awardIcon: ImageView = view.findViewById(R.id.award_icon)
        private val awardDate: TextView = view.findViewById(R.id.award_date)

        fun bind(award: Award) {
            // Formatear fecha
            award.premiationDate.let { dateStr ->
                try {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val outputFormat = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
                    val date = inputFormat.parse(dateStr)
                    val formattedDate = date?.let { outputFormat.format(it) }
                    awardDate.text = formattedDate ?: dateStr
                } catch (e: Exception) {
                    awardDate.text = dateStr
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_award, parent, false)
        return AwardViewHolder(view)
    }

    override fun onBindViewHolder(holder: AwardViewHolder, position: Int) {
        holder.bind(awards[position])
    }

    override fun getItemCount() = awards.size

    fun updateAwards(newAwards: List<Award>) {
        awards = newAwards
        notifyDataSetChanged()
    }
}

