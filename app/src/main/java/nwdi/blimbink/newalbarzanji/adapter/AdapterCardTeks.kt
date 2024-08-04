package nwdi.blimbink.newalbarzanji.adapter

import nwdi.blimbink.newalbarzanji.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterCardTeks(private val items: List<Pair<String, String>>) :
    RecyclerView.Adapter<AdapterCardTeks.TextViewHolder>() {

    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardTeksOn: TextView = view.findViewById(R.id.card_teks_on)
        val cardTeksOff: TextView = view.findViewById(R.id.card_teks_off)

        init {
            cardTeksOn.setOnClickListener {
                cardTeksOff.visibility = if (cardTeksOff.visibility == View.VISIBLE) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_teks, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val (textOn, textOff) = items[position]
        holder.cardTeksOn.text = textOn
        holder.cardTeksOff.text = textOff
        // Set initial visibility based on your requirements
        holder.cardTeksOff.visibility = View.GONE
    }

    override fun getItemCount(): Int = items.size
}
