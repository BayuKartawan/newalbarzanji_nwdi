package nwdi.blimbink.newalbarzanji.lagu

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nwdi.blimbink.newalbarzanji.R
import nwdi.blimbink.newalbarzanji.adapter.AdapterCardTeks

data class ItemLaguAhlanbiwaf(
    val textOn: String,
    val textOff: String
)

class LaguAhlanbiwafFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lagu_ahlanbiwaf, container, false)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_lagu_ahlan)
        toolbar.inflateMenu(R.menu.toolbar_play)
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_play_stop -> {
                    if (isPlaying) {
                        mediaPlayer?.pause()
                        item.icon = resources.getDrawable(R.drawable.icon_play, null)
                        item.title = "Play"
                    } else {
                        mediaPlayer?.start()
                        item.icon = resources.getDrawable(R.drawable.icon_pause, null)
                        item.title = "Stop"
                    }
                    isPlaying = !isPlaying
                    true
                }
                else -> false
            }
        }

        // Initialize RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview_lagu_ahlanbiwaf)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Read and parse JSON file
        val jsonString =
            context?.resources?.openRawResource(R.raw.items_lagu_ahlanbiwaf)?.bufferedReader()
                .use { it?.readText() }
        val itemsType = object : TypeToken<List<ItemLaguAhlanbiwaf>>() {}.type
        val items: List<ItemLaguAhlanbiwaf> = Gson().fromJson(jsonString, itemsType)

        val adapter = AdapterCardTeks(items.map { it.textOn to it.textOff })
        recyclerView.adapter = adapter

        // Setup MediaPlayer
        mediaPlayer = MediaPlayer.create(context, R.raw.lagu_ahlanbiwaf)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
