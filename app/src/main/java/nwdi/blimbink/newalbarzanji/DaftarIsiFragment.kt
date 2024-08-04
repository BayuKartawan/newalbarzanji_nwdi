package nwdi.blimbink.newalbarzanji

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nwdi.blimbink.newalbarzanji.adapter.ButtonAdapter
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarAlbarzanjiFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarDoadoaFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarDoapusakaFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarIilahitamFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarLillahizainuddinFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarQiroatulfatihahFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarSholawatbadriahFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarSholawatnahdlatainFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarSurahyaasinFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarSholawatfatihFragment
import nwdi.blimbink.newalbarzanji.daftarisi.DaftarTholaalbadruFragment
import nwdi.blimbink.newalbarzanji.model.ItemDaftarIsi
import java.io.InputStreamReader

class DaftarIsiFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ButtonAdapter
    private lateinit var items: List<ItemDaftarIsi>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daftar_isi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menyembunyikan btn_toggle_nav di DaftarIsiFragment
        (activity as? MainActivity)?.setBtnToggleNavVisibility(View.GONE)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        items =
            readItemsFromJson() // Lazy loading: hanya baca data sekali saat pertama kali digunakan
        adapter = ButtonAdapter(items.map { it.name }) { itemName ->
            val item = items.find { it.name == itemName }
            item?.let {
                navigateToDetailFragment(it.id)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun readItemsFromJson(): List<ItemDaftarIsi> {
        val inputStream = resources.openRawResource(R.raw.items_daftar_isi)
        val reader = InputStreamReader(inputStream)
        val itemType = object : TypeToken<List<ItemDaftarIsi>>() {}.type
        return Gson().fromJson(reader, itemType)
    }

    private fun navigateToDetailFragment(itemId: Int) {
        val fragment = when (itemId) {
            1 -> DaftarQiroatulfatihahFragment()
            2 -> DaftarSurahyaasinFragment()
            3 -> DaftarSholawatfatihFragment()
            4 -> DaftarAlbarzanjiFragment()
            5 -> DaftarTholaalbadruFragment()
            6 -> DaftarIilahitamFragment()
            7 -> DaftarSholawatbadriahFragment()
            8 -> DaftarSholawatnahdlatainFragment()
            9 -> DaftarLillahizainuddinFragment()
            10 -> DaftarDoapusakaFragment()
            11 -> DaftarDoadoaFragment()
            else -> null
        }
        fragment?.let {
            val bundle = Bundle().apply {
                putInt("ITEM_ID", itemId)
            }
            it.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.frame_container, it)
                .addToBackStack(null)
                .commit()
        }
    }

}
