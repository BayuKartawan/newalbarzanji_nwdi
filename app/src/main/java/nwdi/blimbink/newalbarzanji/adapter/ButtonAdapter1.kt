package nwdi.blimbink.newalbarzanji.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.DetailActivity
import nwdi.blimbink.newalbarzanji.R

class ButtonAdapter1(
    private val items: List<String>
) : RecyclerView.Adapter<ButtonAdapter1.ButtonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_button, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text = item
        holder.cardView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EXTRA_TITLE", item)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = items.size

    class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.card_view_item)
        val textView: TextView = view.findViewById(R.id.button_item)
    }
}
