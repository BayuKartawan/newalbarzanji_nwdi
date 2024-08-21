package nwdi.blimbink.newalbarzanji

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nwdi.blimbink.newalbarzanji.adapter.ButtonAdapter

class DaftarIsiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lagu, container, false)

        val items = listOf(
            "Qiroatul Fatihah\n( Qiroatul Fatihah Lisayyidina )",
            "Surah YaaSin\n( YaaSin )",
            "Sholawat Fatih\n(  )",
            "Al - Barzanji\n( Aljannatu wa na'imuha )",
            "Tholaal Badru ‘Alaina\n( Thola'al badruu alaina )",
            "Iilahitam\n( Iilaahitammiminna'ma )",
            "Sholawat Badriah\n( Sholatullah Shlamullah )",
            "Sholawat Nahdlatain\n( Allahummainnanasaluka )",
            "Lillahi Zainuddin\n( Lillahizainuddini fi fadlihi )",
            "Do’a Pusaka\n( Robbanampa'nabima 'allamtana )",
            "Do’a Do’a\n( صَلِّ وَسَلِّمْ عَلَى النَّبِيِّ )",
            ""
        )
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_lagu)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ButtonAdapter(items) { item ->
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra("ITEM_TITLE", item)
            startActivity(intent)
        }

        return view
    }
}
