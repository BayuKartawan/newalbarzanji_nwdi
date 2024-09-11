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

// Data class untuk menyimpan item baca dengan teks yang ditampilkan dan tersembunyi
data class ItemBaca(
    val textOn: String,
    val textOff: String
)

// Data class untuk deskripsi yang berisi judul dan daftar item baca
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
        setHasOptionsMenu(true) // Menandakan bahwa fragment ini memiliki menu
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Menginflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_baca, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menampilkan tombol navigasi di activity utama
        (activity as? MainActivity)?.setBtnToggleNavVisibility(View.VISIBLE)

        // Menginisialisasi RecyclerView dan menetapkan layout manager
        recyclerView = view.findViewById(R.id.recyclerview_baca)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Mengatur Toolbar sebagai ActionBar
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_baca)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        // Memuat data item dari file JSON
        items = loadItemsFromJson()

        // Mengatur adapter untuk RecyclerView
        adapter = AdapterCardTeks(requireContext(), items.map { it.textOn to it.textOff })
        recyclerView.adapter = adapter
    }

    // Memuat item dari file JSON berdasarkan judul yang dipilih
    private fun loadItemsFromJson(): List<ItemBaca> {
        val titlesToDisplay = listOf(
            "Qiroatul Fatihah\n( قِرَاءَةُ الْفَاتِحَة )",
            "Sholawat Nahdlatain\n( اَللَّهُمَّ إِنَّا نَسْأَلُكَ بِك )",
            "Sholawat Fatih\n( صَلاَةُ الْفَاتِحِ )",
            "Al - Barzanji\n( اَلْجَنَّةُ وَ نَعِيْمُهَا )",
            "Tholaal Badru ‘Alaina\n( طَلَعَ الْبَدْرُ عَلَيْنَا )",
            "Sholawat Badriah\n( صَـلاَ ةُ اللهِ سَـلاَ مُ الله )",
            "Iilahitam\n( اِلٰهِىْ تَمِّمِ النَّعْمَآ عَلَيْنَا )",
            "Do’a Do’a\n( صَلِّ وَسَلِّمْ عَلَى النَّبِيِّ )"
        )

        // Membaca JSON dari assets
        val jsonString = requireContext().assets.open("descriptions.json").bufferedReader().use { it.readText() }
        val descriptions: List<Description> =
            Gson().fromJson(jsonString, object : TypeToken<List<Description>>() {}.type)

        // Membuat daftar item yang terurut sesuai judul yang ditampilkan
        val orderedItems = mutableListOf<ItemBaca>()

        // Mengisi orderedItems dengan item deskripsi berdasarkan judul yang ditampilkan
        titlesToDisplay.forEach { title ->
            descriptions.find { it.title == title }?.descriptionItems?.let { orderedItems.addAll(it) }
        }

        return orderedItems
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Menginflate menu dari resource
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Menentukan posisi item dalam daftar berdasarkan ID menu
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
            // Melakukan scroll halus ke posisi yang ditentukan dan menyesuaikan tampilan
            recyclerView.smoothScrollToPosition(position)
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        recyclerView.removeOnScrollListener(this)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        layoutManager.scrollToPositionWithOffset(position, 0)
                    }
                }
            })
        }

        return super.onOptionsItemSelected(item)
    }
}
