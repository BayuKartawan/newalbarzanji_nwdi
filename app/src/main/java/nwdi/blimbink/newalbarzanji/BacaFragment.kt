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

        // Load items from raw resources
        items = loadItemsFromRawResources()

        // Set up the adapter
        adapter = AdapterCardTeks(requireContext(), items.map { it.textOn to it.textOff })
        recyclerView.adapter = adapter
    }

    private fun loadItemsFromRawResources(): List<ItemBaca> {
        val rawResources = listOf(
            R.raw.items_daftarisi_qiroatul,
            R.raw.items_daftarisi_sholawatnahdlatain,
            R.raw.items_daftarisi_sholawatfatih,
            R.raw.items_daftarisi_albarzanji,
            R.raw.items_daftarisi_tholaalbadru,
            R.raw.items_daftarisi_iilahitam,
            R.raw.items_daftarisi_sholawatbadriah,
            R.raw.items_daftarisi_doadoa
        )

        val items = mutableListOf<ItemBaca>()
        val gson = Gson()
        val itemsType = object : TypeToken<List<ItemBaca>>() {}.type

        rawResources.forEach { resId ->
            val jsonString =
                context?.resources?.openRawResource(resId)?.bufferedReader().use { it?.readText() }
            jsonString?.let {
                val newItems: List<ItemBaca> = gson.fromJson(it, itemsType)
                items.addAll(newItems)
            }
        }

        return items
    }

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
