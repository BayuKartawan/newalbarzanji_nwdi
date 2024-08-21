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

class LaguFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_lagu, container, false)

        val items = listOf(
            "Antiya Pancor\n( اَنْتِ يَا فَنْجُوْر بِلَادِى )",
            "Hayya Ganunna Syidanan\n( هَيَّا غَنُوْا نَشِيْدَنَا )",
            "Ya Dzal Jala Liwal Iqram\n( يَا ذَاالْجَلَالِ وَالْاِكْرَامْ )",
            "Nahdlatain\n( Nahdlatul Wathan setia )",
            "Obat Sakit Jahil\n( Sakit Jahil nde' narak oatne )",
            "Ahlan Biwaf Bizairi & Pacu Gama’\n( اَهْلًا بِوَفْدِ زَائِرِيْن )",
            "Mars Ma’had\n()",
            "Tanawwaroma\n(Tanawwaroma)",
            "Bersatu Haluwan\n(HayyaGhonunnasidana)",
            "Mars NW\n()",
            "NWDI Daiman Abada\n()",
            "Mars NWDI\n()",
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
