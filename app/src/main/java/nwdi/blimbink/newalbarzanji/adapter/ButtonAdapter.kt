package nwdi.blimbink.newalbarzanji.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.R

class ButtonAdapter(
    private var items: List<String>, // Ubah 'val' menjadi 'var'
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<String>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_button, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item
        holder.cardView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size

    class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.card_view_item)
        val textView: TextView = view.findViewById(R.id.button_item)
    }

}
