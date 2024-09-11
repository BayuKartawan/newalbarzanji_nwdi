package nwdi.blimbink.newalbarzanji.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.R
import nwdi.blimbink.newalbarzanji.utils.TextAnimationUtils

@Suppress("DEPRECATION")
class AdapterCardTeks(
    private val context: Context,  // Konteks aplikasi untuk mengakses sumber daya
    private val items: List<Pair<String, String>>  // Daftar item yang akan ditampilkan, masing-masing item adalah pasangan teks
) : RecyclerView.Adapter<AdapterCardTeks.TextViewHolder>() {

    // Kelas untuk menyimpan referensi ke tampilan dalam item RecyclerView
    inner class TextViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardTeksOn: TextView = view.findViewById(R.id.card_teks_on)  // Teks yang selalu terlihat
        val cardTeksOff: TextView = view.findViewById(R.id.card_teks_off)  // Teks yang akan ditampilkan saat di-click

        private var isAnimating = false  // Menandakan apakah animasi sedang berlangsung

        init {
            // Menangani klik pada cardTeksOn
            cardTeksOn.setOnClickListener {
                // Jika animasi sedang berlangsung, tidak melakukan apa-apa
                if (isAnimating) return@setOnClickListener

                if (cardTeksOff.visibility == View.VISIBLE) {
                    // Menyembunyikan teks dengan animasi
                    TextAnimationUtils.hideTextWithAnimation(
                        cardTeksOff,
                        TextAnimationUtils.calculateDuration(cardTeksOff.text.length)
                    )
                } else {
                    // Menampilkan teks dan menambahkan animasi
                    cardTeksOff.visibility = View.VISIBLE
                    cardTeksOff.text = items[adapterPosition].second
                    TextAnimationUtils.showTextWithAnimation(
                        cardTeksOff,
                        cardTeksOff.text.toString(),
                        TextAnimationUtils.calculateDuration(cardTeksOff.text.length)
                    ) {
                        isAnimating = false  // Mengatur status animasi setelah animasi selesai
                    }
                }
            }
        }
    }

    // Membuat ViewHolder baru untuk item RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_teks, parent, false)
        return TextViewHolder(view)
    }

    // Mengikat data ke ViewHolder
    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val (textOn, textOff) = items[position]
        holder.cardTeksOn.text = textOn
        holder.cardTeksOff.text = textOff

        // Mengatur visibilitas awal dari cardTeksOff
        holder.cardTeksOff.visibility = if (textOff.isEmpty()) View.GONE else View.GONE

        // Mengambil pengaturan ukuran font dari SharedPreferences
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val fontSizeLatin = sharedPreferences.getInt("FontSizeLatin", 16)
        val fontSizeArabic = sharedPreferences.getInt("FontSizeArabic", 30)

        // Mengatur ukuran font berdasarkan bahasa teks
        holder.cardTeksOn.textSize = getFontSize(textOn, fontSizeLatin, fontSizeArabic)
        holder.cardTeksOff.textSize = getFontSize(textOff, fontSizeLatin, fontSizeArabic)

        // Mengatur latar belakang berdasarkan karakter khusus
        setBackgroundBasedOnCharacter(holder, textOn)
    }

    // Mengembalikan jumlah item dalam adapter
    override fun getItemCount(): Int = items.size

    // Fungsi untuk memeriksa apakah teks berbahasa Arab
    private fun isArabicText(text: String): Boolean {
        val arabicRegex = Regex("[\\u0600-\\u06FF]")
        return arabicRegex.containsMatchIn(text)
    }

    // Fungsi untuk mendapatkan ukuran font berdasarkan bahasa teks
    private fun getFontSize(text: String, fontSizeLatin: Int, fontSizeArabic: Int): Float {
        return if (isArabicText(text)) fontSizeArabic.toFloat() else fontSizeLatin.toFloat()
    }

    // Fungsi untuk mengatur latar belakang berdasarkan karakter khusus
    private fun setBackgroundBasedOnCharacter(holder: TextViewHolder, textOn: String) {
        val backgroundResource = if (textOn.contains("۞")) {
            R.drawable.latarpenanda  // Menggunakan gambar latar khusus
        } else {
            R.color.transparent  // Latar belakang transparan jika tidak ada karakter khusus
        }
        holder.cardTeksOn.setBackgroundResource(backgroundResource)
        holder.cardTeksOff.setBackgroundResource(backgroundResource)
    }
}
