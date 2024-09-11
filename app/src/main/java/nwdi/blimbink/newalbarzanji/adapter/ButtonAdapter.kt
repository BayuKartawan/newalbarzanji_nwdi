package nwdi.blimbink.newalbarzanji.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.R

// Kelas adapter untuk RecyclerView yang menampilkan item dalam bentuk tombol
class ButtonAdapter(
    private var items: List<String>, // Daftar item yang akan ditampilkan di RecyclerView
    private val onItemClick: (String) -> Unit // Fungsi callback untuk menangani klik item
) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    // Fungsi untuk memperbarui data dan memberi tahu adapter agar RecyclerView diperbarui
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged() // Memberi tahu RecyclerView bahwa data telah berubah
    }

    // Membuat dan mengembalikan ViewHolder baru
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        // Meng-inflate layout item_button menjadi tampilan
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_button, parent, false)
        return ButtonViewHolder(view) // Mengembalikan ViewHolder yang baru dibuat
    }

    // Mengikat data ke ViewHolder pada posisi tertentu
    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val item = items[position] // Mendapatkan item pada posisi saat ini
        holder.bind(item) // Mengikat item ke ViewHolder
    }

    // Mengembalikan jumlah item yang ada di daftar
    override fun getItemCount() = items.size

    // Kelas ViewHolder untuk menampilkan item
    inner class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cardView: CardView = view.findViewById(R.id.card_view_item) // CardView untuk menampilkan item
        private val textView: TextView = view.findViewById(R.id.button_item) // TextView untuk menampilkan teks item

        // Fungsi untuk mengikat data item ke tampilan
        fun bind(item: String) {
            textView.text = item // Menampilkan teks item di TextView
            cardView.setOnClickListener { onItemClick(item) } // Menangani klik pada CardView
        }
    }
}
