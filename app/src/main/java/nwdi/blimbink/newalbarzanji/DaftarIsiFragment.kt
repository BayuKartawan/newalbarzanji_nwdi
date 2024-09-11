package nwdi.blimbink.newalbarzanji

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.adapter.ButtonAdapter

// Fragment untuk menampilkan daftar isi
class DaftarIsiFragment : Fragment() {

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

        // Mengatur visibilitas tombol toggle navigation
        (activity as? MainActivity)?.setBtnToggleNavVisibility(View.GONE)

        // Mengakses toolbar menggunakan ID
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_listitem)

        // Mengubah title toolbar
        toolbar.title = "Daftar Isi" // Mengganti dengan teks baru
    }

    // Mengatur RecyclerView dengan data daftar isi dan adapter
    private fun setupRecyclerView(view: View) {
        // Daftar item daftar isi yang akan ditampilkan
        originalItems = listOf(
            "Qiroatul Fatihah\n( قِرَاءَةُ الْفَاتِحَة )",
            "Surah YaaSin\n( يٰسٓ )",
            "Sholawat Fatih\n( صَلاَةُ الْفَاتِحِ )",
            "Al - Barzanji\n( اَلْجَنَّةُ وَ نَعِيْمُهَا )",
            "Tholaal Badru ‘Alaina\n( طَلَعَ الْبَدْرُ عَلَيْنَا )",
            "Iilahitam\n( اِلٰهِىْ تَمِّمِ النَّعْمَآ عَلَيْنَا )",
            "Sholawat Badriah\n( صَـلاَ ةُ اللهِ سَـلاَ مُ الله )",
            "Sholawat Nahdlatain\n( اَللَّهُمَّ إِنَّا نَسْأَلُكَ بِك )",
            "Lillahi Zainuddin\n( لِلّٰهِ زَيْنُ الـدِّيْـن )",
            "Do’a Pusaka\n( رَ بَّنَا ا نْفَعْنَا ِبمَا عَلَّمْتَنَا )",
            "Do’a Do’a\n( صَلِّ وَسَلِّمْ عَلَى النَّبِيِّ )"
        )

        // Inisialisasi adapter dengan data daftar isi dan listener untuk setiap item
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

    // Mengatur SearchView untuk mencari daftar isi dalam daftar
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
