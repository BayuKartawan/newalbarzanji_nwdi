package nwdi.blimbink.newalbarzanji.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.R
import nwdi.blimbink.newalbarzanji.DescriptionItem

class DescriptionAdapter(
    private val items: List<DescriptionItem>
) : RecyclerView.Adapter<DescriptionAdapter.DescriptionViewHolder>() {

    inner class DescriptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teksOn: TextView = view.findViewById(R.id.card_teks_on)
        val teksOff: TextView = view.findViewById(R.id.card_teks_off)

        init {
            teksOn.setOnClickListener {
                teksOff.visibility = if (teksOff.visibility == View.VISIBLE) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DescriptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_teks, parent, false)
        return DescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DescriptionViewHolder, position: Int) {

        val (textOn, textOff) = items[position]
        holder.teksOn.text = textOn
        holder.teksOff.text = textOff
        // Set initial visibility based on your requirements
        holder.teksOff.visibility = View.GONE
    }

    override fun getItemCount(): Int = items.size
}
