package nwdi.blimbink.newalbarzanji

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nwdi.blimbink.newalbarzanji.adapter.AdapterCardTentang

data class Item(
    val textOn: String,
    val textOff: String
)

class TentangFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tentang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menyembunyikan btn_toggle_nav
        (activity as? MainActivity)?.setBtnToggleNavVisibility(View.GONE)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview_tentang)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Read and parse JSON file
        val jsonString = context?.resources?.openRawResource(R.raw.items_tentang)?.bufferedReader()
            .use { it?.readText() }
        val itemsType = object : TypeToken<List<Item>>() {}.type
        val items: List<Item> = Gson().fromJson(jsonString, itemsType)

        val adapter = AdapterCardTentang(items.map { it.textOn to it.textOff })
        recyclerView.adapter = adapter

        // Handle ImageButton click
        val chatButton: ImageButton = view.findViewById(R.id.tentang_chat)
        chatButton.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
    }
}
