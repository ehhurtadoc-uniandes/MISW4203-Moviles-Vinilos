package co.uniandes.grupo11.vinilos.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.uniandes.grupo11.vinilos.R
import co.uniandes.grupo11.vinilos.models.Collector

class CollectorsAdapter : RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>() {
    
    private var collectors: List<Collector> = emptyList()

    class CollectorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val collectorImage: ImageView = view.findViewById(R.id.collector_image)
        private val collectorName: TextView = view.findViewById(R.id.collector_name)
        private val collectorEmail: TextView = view.findViewById(R.id.collector_email)
        private val collectorPhone: TextView = view.findViewById(R.id.collector_phone)

        fun bind(collector: Collector) {
            collectorName.text = collector.name
            collectorEmail.text = collector.email
            collectorPhone.text = collector.telephone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_collector, parent, false)
        return CollectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.bind(collectors[position])
    }

    override fun getItemCount() = collectors.size

    fun updateCollectors(newCollectors: List<Collector>) {
        collectors = newCollectors
        notifyDataSetChanged()
    }
}

