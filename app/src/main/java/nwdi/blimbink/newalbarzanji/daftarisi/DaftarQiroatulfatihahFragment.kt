package nwdi.blimbink.newalbarzanji.daftarisi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nwdi.blimbink.newalbarzanji.R
import nwdi.blimbink.newalbarzanji.adapter.AdapterCardTeks

data class ItemDaftarQiroatul(
    val textOn: String,
    val textOff: String
)

class DaftarQiroatulfatihahFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daftar_qiroatulfatihah, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview_daftar_qiroatulfatihah)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Read and parse JSON file
        val jsonString =
            context?.resources?.openRawResource(R.raw.items_daftarisi_qiroatul)?.bufferedReader()
                .use { it?.readText() }
        val itemsType = object : TypeToken<List<ItemDaftarQiroatul>>() {}.type
        val items: List<ItemDaftarQiroatul> = Gson().fromJson(jsonString, itemsType)

        val adapter = AdapterCardTeks(items.map { it.textOn to it.textOff })
        recyclerView.adapter = adapter
    }
}
