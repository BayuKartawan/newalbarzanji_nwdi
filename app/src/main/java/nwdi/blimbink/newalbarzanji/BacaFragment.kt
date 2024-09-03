package nwdi.blimbink.newalbarzanji

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nwdi.blimbink.newalbarzanji.adapter.AdapterCardTeks

data class ItemBaca(
    val textOn: String,
    val textOff: String
)

data class Description(
    val title: String,
    val descriptionItems: List<ItemBaca>
)

@Suppress("DEPRECATION")
class BacaFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var items: List<ItemBaca>
    private lateinit var adapter: AdapterCardTeks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Mengindikasikan bahwa fragment memiliki menu
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_baca, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengatur visibilitas btn_toggle_nav menjadi VISIBLE
        (activity as? MainActivity)?.setBtnToggleNavVisibility(View.VISIBLE)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerview_baca)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set Toolbar as ActionBar
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_baca)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        // Load items from descriptions.json
        items = loadItemsFromJson()

        // Set up the adapter
        adapter = AdapterCardTeks(requireContext(), items.map { it.textOn to it.textOff })
        recyclerView.adapter = adapter
    }

    // Load specific items from descriptions.json based on selected titles
    private fun loadItemsFromJson(): List<ItemBaca> {
        // Titles that we want to display
        val titlesToDisplay = listOf(
            "Qiroatul Fatihah\n( قِرَاءَةُ الْفَاتِحَة )",
            "Sholawat Nahdlatain\n( اَللَّهُمَّ إِنَّا نَسْأَلُكَ بِك )",
            "Sholawat Fatih\n( صَلاَةُ الْفَاتِحِ )",
            "Al - Barzanji\n( اَلْجَنَّةُ وَ نَعِيْمُهَا )",
            "Tholaal Badru ‘Alaina\n( طَلَعَ الْبَدْرُ عَلَيْنَا )",
            "Sholawat Badriah\n( صَـلاَ ةُ اللهِ سَـلاَ مُ الله )",
            "Iilahitam\n( اِلٰهِىْ تَمِّمِ النَّعْمَآ عَلَيْنَا )",
            "Do’a Do’a\n( صَلِّ وَسَلِّمْ عَلَى النَّبِيِّ )",
        )

        // Load JSON from assets
        val jsonString = requireContext().assets.open("descriptions.json").bufferedReader().use { it.readText() }
        val descriptions: List<Description> =
            Gson().fromJson(jsonString, object : TypeToken<List<Description>>() {}.type)

        // Create a mutable list to hold the items in the correct order
        val orderedItems = mutableListOf<ItemBaca>()

        // Iterate through the titlesToDisplay in the specified order
        for (title in titlesToDisplay) {
            // Find the corresponding description for each title
            val description = descriptions.find { it.title == title }
            // If found, add all its descriptionItems to the orderedItems list
            description?.descriptionItems?.let { orderedItems.addAll(it) }
        }

        return orderedItems    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val position = when (item.itemId) {
            R.id.menu_items_daftarisi_qiroatul -> items.indexOfFirst { it.textOn == "۞ قِرَاءَةُ الْفَاتِحَةِ ۞" }
            R.id.menu_items_daftarisi_sholawatnahdlatain -> items.indexOfFirst { it.textOn == "۞ صَلاَةُ النَّهْضَتَيْنِ ۞" }
            R.id.menu_items_daftarisi_sholawatfatih -> items.indexOfFirst { it.textOn == "۞ صَلاَةُ الْفَاتِحِ ۞" }
            R.id.menu_items_daftarisi_albarzanji -> items.indexOfFirst { it.textOn == "۞ مولد البرزنجى نظم ۞" }
            R.id.menu_items_daftarisi_tholaalbadru -> items.indexOfFirst { it.textOn == "۞ يقرأ عند القيام ۞" }
            R.id.menu_items_daftarisi_iilahitam -> items.indexOfFirst { it.textOn == "۞ الهى تمم النعما علينا ۞" }
            R.id.menu_items_daftarisi_sholawatbadriah -> items.indexOfFirst { it.textOn == "۞ الصّلٰوات البدرية ۞" }
            R.id.menu_items_daftarisi_doadoa -> items.indexOfFirst { it.textOn == "۞ الخاتمة ۞" }
            else -> -1
        }

        if (position != -1) {
            // Langkah 1: Smooth scroll to the position
            recyclerView.smoothScrollToPosition(position)

            // Langkah 2: Setelah smooth scroll selesai, gunakan scrollToPositionWithOffset
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        // Menghapus listener untuk menghindari callback yang tidak perlu
                        recyclerView.removeOnScrollListener(this)

                        // Pindahkan item ke bagian atas layar
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        layoutManager.scrollToPositionWithOffset(position, 0)
                    }
                }
            })
        }

        return super.onOptionsItemSelected(item)
    }
}
