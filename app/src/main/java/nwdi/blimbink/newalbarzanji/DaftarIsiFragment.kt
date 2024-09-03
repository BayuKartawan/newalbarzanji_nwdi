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

class DaftarIsiFragment : Fragment() {

    private lateinit var adapter: ButtonAdapter
    private lateinit var originalItems: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar, container, false)
        // Mengakses Toolbar
        val toolbar: Toolbar = view.findViewById(R.id.toolbar_listitem)

        // Mengubah title dari toolbar
        toolbar.title = "Daftar isi"

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
            "Do’a Do’a\n( صَلِّ وَسَلِّمْ عَلَى النَّبِيِّ )",
        )

        adapter = ButtonAdapter(originalItems) { item ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("ITEM_TITLE", item)
            startActivity(intent)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_lagu)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val searchView = view.findViewById<SearchView>(R.id.search_daftar)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredItems = if (!newText.isNullOrEmpty()) {
                    originalItems.filter { it.contains(newText, ignoreCase = true) }
                } else {
                    originalItems
                }
                adapter.updateData(filteredItems)
                return true
            }

        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Mengatur visibilitas btn_toggle_nav
        (activity as? MainActivity)?.setBtnToggleNavVisibility(View.GONE)
    }
}
