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

class LaguFragment : Fragment() {

    private lateinit var adapter: ButtonAdapter
    private lateinit var originalItems: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar, container, false)

        originalItems = listOf(
            "Antiya Pancor\n( اَنْتِ يَا فَنْجُوْر بِلَادِى )",
            "Yaa Fata Sasak\n( هَيَّا غَنُوْا نَشِيْدَنَا )",
            "Ya Dzal Jala Liwal Iqram\n( يَا ذَاالْجَلَالِ وَالْاِكْرَامْ )",
            "Nahdlatain\n( Nahdlatul Wathan setia )",
            "Ya man yaru mul ula\n(يا من يروم العلى )",
            "Obat Sakit Jahil\n( Sakit Jahil nde' narak oatne )",
            "Ahlan Biwaf Bizairi\n( اَهْلًا بِوَفْدِ زَائِرِيْن )",
            "Mars Ma’had\n(Ma’had darul qur’an...)",
            "Pacu Gama’\n(Inaq amaqku...)",
            "Tanawwaro\n(تنور محفلنا فبدا )",
            "Bersatu Haluwan\n(امامنا الشافعى)",
            "Kami benihan\n(Kami benihan...)",
            "Mars NWDI\n(Cinta teguh pada agama...)",
            "NWDI Daiman Abada\n(Di tamaan sanubari...)",
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
