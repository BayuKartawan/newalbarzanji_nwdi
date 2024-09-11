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

// Data class untuk mendefinisikan struktur data item yang akan ditampilkan
data class Item(
    val textOn: String,
    val textOff: String
)

class TentangFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_tentang, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup RecyclerView dan tombol chat, serta menyembunyikan tombol navigasi
        setupRecyclerView(view)
        setupChatButton(view)
        hideToggleNavButton()
    }

    private fun setupRecyclerView(view: View) {
        // Mengatur RecyclerView dengan layout manager dan adapter
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview_tentang)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Memuat data dari file JSON dan mengaturnya ke adapter
        val items = loadItemsFromAssets()
        val adapter = AdapterCardTentang(items.map { it.textOn to it.textOff })
        recyclerView.adapter = adapter
    }

    private fun loadItemsFromAssets(): List<Item> {
        // Membaca data JSON dari assets dan mengkonversinya menjadi list Item
        val jsonString = requireContext().assets.open("items_tentang.json").bufferedReader().use { it.readText() }
        return Gson().fromJson(jsonString, object : TypeToken<List<Item>>() {}.type)
    }

    private fun setupChatButton(view: View) {
        // Mengatur tombol setelan untuk membuka SettingActivity saat diklik
        val setelanButton: ImageButton = view.findViewById(R.id.tentang_setelan)
        setelanButton.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun hideToggleNavButton() {
        // Menyembunyikan tombol navigasi jika activity adalah MainActivity
        (activity as? MainActivity)?.setBtnToggleNavVisibility(View.GONE)
    }
}
