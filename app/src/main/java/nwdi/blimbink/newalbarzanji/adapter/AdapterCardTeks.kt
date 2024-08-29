package nwdi.blimbink.newalbarzanji.adapter

import android.content.Context
import android.content.SharedPreferences
import nwdi.blimbink.newalbarzanji.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AdapterCardTeks(private val context: Context, private val items: List<Pair<String, String>>) :
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

        // Get SharedPreferences for font size settings
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val fontSizeTextOn = sharedPreferences.getInt("FontSizeTextOn", 30) // Default size 20sp
        val fontSizeTextOff = sharedPreferences.getInt("FontSizeTextOff", 16) // Default size 18sp

        // Set font size based on the saved preferences
        holder.cardTeksOn.textSize = fontSizeTextOn.toFloat()
        holder.cardTeksOff.textSize = fontSizeTextOff.toFloat()

        // Check if textOn contains the special character (۞)
        if (textOn.contains("۞")) {
            // Change background color if condition is met
            holder.cardTeksOn.setBackgroundResource(R.drawable.latarpenanda)
            holder.cardTeksOff.setBackgroundResource(R.drawable.latarpenanda)
        } else {
            // Optional: Set a default background color if the condition is not met
            holder.cardTeksOn.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.transparent))
            holder.cardTeksOff.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.transparent))
        }
    }

    override fun getItemCount(): Int = items.size
}
