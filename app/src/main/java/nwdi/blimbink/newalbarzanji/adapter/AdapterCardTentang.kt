package nwdi.blimbink.newalbarzanji.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.R
import nwdi.blimbink.newalbarzanji.utils.TextAnimationUtils

@Suppress("DEPRECATION")
class AdapterCardTentang(private val items: List<Pair<String, String>>) :
    RecyclerView.Adapter<AdapterCardTentang.TextViewHolder>() {

    // Kelas ViewHolder untuk mengelola tampilan setiap item
    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Inisialisasi komponen tampilan dari layout item
        val cardTeksOn: TextView = view.findViewById(R.id.itemcard_tentang_on)
        val cardTeksOff: TextView = view.findViewById(R.id.itemcard_tentang_off)

        private var isAnimating = false // Status animasi

        init {
            // Set listener untuk menangani klik pada cardTeksOn
            cardTeksOn.setOnClickListener {
                if (isAnimating) return@setOnClickListener // Jangan lanjutkan jika sedang animasi

                if (cardTeksOff.visibility == View.VISIBLE) {
                    // Jika cardTeksOff terlihat, sembunyikan dengan animasi
                    TextAnimationUtils.hideTextWithAnimation(
                        cardTeksOff,
                        TextAnimationUtils.calculateDuration(cardTeksOff.text.length)
                    )
                } else {
                    // Jika cardTeksOff tidak terlihat, tampilkan dan animasikan
                    cardTeksOff.visibility = View.VISIBLE
                    cardTeksOff.text = items[adapterPosition].second
                    TextAnimationUtils.showTextWithAnimation(
                        cardTeksOff,
                        cardTeksOff.text.toString(),
                        TextAnimationUtils.calculateDuration(cardTeksOff.text.length)
                    ) {
                        isAnimating = false // Set status animasi selesai
                    }
                }
            }
        }
    }

    // Membuat ViewHolder baru saat diperlukan
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_tentang, parent, false)
        return TextViewHolder(view)
    }

    // Mengikat data dengan ViewHolder
    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val (textOn, textOff) = items[position]
        holder.cardTeksOn.text = textOn
        holder.cardTeksOff.text = textOff
        // Set visibilitas awal cardTeksOff ke GONE
        holder.cardTeksOff.visibility = View.GONE
    }

    // Mengembalikan jumlah item dalam adapter
    override fun getItemCount(): Int = items.size
}
