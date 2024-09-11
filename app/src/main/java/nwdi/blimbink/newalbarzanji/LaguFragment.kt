package nwdi.blimbink.newalbarzanji

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.adapter.ButtonAdapter

// Fragment untuk menampilkan daftar lagu
class LaguFragment : Fragment() {

    private lateinit var adapter: ButtonAdapter
    private lateinit var originalItems: List<String>

    // Membuat tampilan fragment dan mengatur RecyclerView dan SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout fragment_daftar untuk tampilan
        val view = inflater.inflate(R.layout.fragment_daftar, container, false)
        setupRecyclerView(view)  // Mengatur RecyclerView
        setupSearchView(view)    // Mengatur SearchView
        return view
    }

    // Mengatur visibilitas tombol toggle navigation saat fragment dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setBtnToggleNavVisibility(View.GONE)
    }

    // Mengatur RecyclerView dengan data lagu dan adapter
    private fun setupRecyclerView(view: View) {
        // Daftar item lagu yang akan ditampilkan
        originalItems = listOf(
            "Antiya Pancor\n( اَنْتِ يَا فَنْجُوْر بِلَادِى )",
            "Yaa Fata Sasak\n( هَيَّا غَنُوْا نَشِيْدَنَا )",
            "Ya Dzal Jala Liwal Iqram\n( يَا ذَاالْجَلَالِ وَالْاِكْرَامْ )",
            "Nahdlatain\n( Nahdlatul Wathan setia )",
            "Ya man yaru mul ula\n(يا من يروم العلى )",
            "Obat Sakit Jahil\n( Sakit Jahil nde' narak oatne )",
            "Ahlan Biwaf Bizairi\n( اَهْلًا بِوَفْدِ زَائِرِيْن )",
            "Mars Hultah NWDI\n(Kini telah tiba saatnya...)",
            "Mars NWDI\n(Cinta teguh pada agama...)",
            "Pacu Gama’\n(Inaq amaqku...)",
            "Tanawwaro\n(تنور محفلنا فبدا )",
            "Bersatu Haluwan\n(امامنا الشافعى)",
            "Kami benihan\n(Kami benihan...)",
            "NWDI Daiman Abada\n(Di tamaan sanubari...)"
        )

        // Inisialisasi adapter dengan data lagu dan listener untuk setiap item
        adapter = ButtonAdapter(originalItems) { item ->
            // Ketika item diklik, buka DetailActivity dan kirimkan data item
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("ITEM_TITLE", item)
            startActivity(intent)
        }

        // Mengatur RecyclerView dengan LinearLayoutManager dan adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_daftar)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    // Mengatur SearchView untuk mencari lagu dalam daftar
    private fun setupSearchView(view: View) {
        val searchView = view.findViewById<SearchView>(R.id.search_daftar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Tidak ada tindakan saat teks pencarian dikirim
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter item berdasarkan teks pencarian dan perbarui data di adapter
                val filteredItems = if (!newText.isNullOrEmpty()) {
                    originalItems.filter { it.contains(newText, ignoreCase = true) }
                } else {
                    originalItems
                }
                adapter.updateData(filteredItems)
                return true
            }
        })
    }
}
