package nwdi.blimbink.newalbarzanji.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.R
import nwdi.blimbink.newalbarzanji.utils.TextAnimationUtils

@Suppress("DEPRECATION")
class AdapterCardTeks(private val context: Context, private val items: List<Pair<String, String>>) :
    RecyclerView.Adapter<AdapterCardTeks.TextViewHolder>() {

    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardTeksOn: TextView = view.findViewById(R.id.card_teks_on)
        val cardTeksOff: TextView = view.findViewById(R.id.card_teks_off)

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
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_teks, parent, false)
        return TextViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val (textOn, textOff) = items[position]
        holder.cardTeksOn.text = textOn
        holder.cardTeksOff.text = textOff

        // Set initial visibility
        holder.cardTeksOff.visibility = if (textOff.isEmpty()) View.GONE else View.GONE

        // Get SharedPreferences for font size settings
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val fontSizeLatin =
            sharedPreferences.getInt("FontSizeLatin", 16) // Default size for Latin text
        val fontSizeArabic =
            sharedPreferences.getInt("FontSizeArabic", 30) // Default size for Arabic text

        // Detect language and set font size accordingly
        holder.cardTeksOn.textSize =
            if (isArabicText(textOn)) fontSizeArabic.toFloat() else fontSizeLatin.toFloat()
        holder.cardTeksOff.textSize =
            if (isArabicText(textOff)) fontSizeArabic.toFloat() else fontSizeLatin.toFloat()

        // Check if textOn contains the special character (۞)
        if (textOn.contains("۞")) {
            // Change background color if condition is met
            holder.cardTeksOn.setBackgroundResource(R.drawable.latarpenanda)
            holder.cardTeksOff.setBackgroundResource(R.drawable.latarpenanda)
        } else {
            // Set a default background color if the condition is not met
            holder.cardTeksOn.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.transparent
                )
            )
            holder.cardTeksOff.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context, R.color.transparent
                )
            )
        }
    }

    override fun getItemCount(): Int = items.size

    // Function to check if the text is in Arabic
    private fun isArabicText(text: String): Boolean {
        val arabicRegex = Regex("[\\u0600-\\u06FF]")
        return arabicRegex.containsMatchIn(text)
    }
}
