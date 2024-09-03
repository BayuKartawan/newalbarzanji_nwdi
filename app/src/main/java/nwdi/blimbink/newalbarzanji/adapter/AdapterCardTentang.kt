package nwdi.blimbink.newalbarzanji.adapter

import nwdi.blimbink.newalbarzanji.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.utils.TextAnimationUtils

@Suppress("DEPRECATION")
class AdapterCardTentang(private val items: List<Pair<String, String>>) :
    RecyclerView.Adapter<AdapterCardTentang.TextViewHolder>() {

    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardTeksOn: TextView = view.findViewById(R.id.itemcard_tentang_on)
        val cardTeksOff: TextView = view.findViewById(R.id.itemcard_tentang_off)

        private var isAnimating = false
        init {
            cardTeksOn.setOnClickListener {
                if (isAnimating) return@setOnClickListener

                if (cardTeksOff.visibility == View.VISIBLE) {
                    // Hide the text with animation
                    TextAnimationUtils.hideTextWithAnimation(cardTeksOff, TextAnimationUtils.calculateDuration(cardTeksOff.text.length))
                } else {
                    // Make the text visible and animate it
                    cardTeksOff.visibility = View.VISIBLE
                    cardTeksOff.text = items[adapterPosition].second
                    TextAnimationUtils.showTextWithAnimation(
                        cardTeksOff,
                        cardTeksOff.text.toString(),
                        TextAnimationUtils.calculateDuration(cardTeksOff.text.length)
                    ) {
                        isAnimating = false
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_tentang, parent, false)
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

