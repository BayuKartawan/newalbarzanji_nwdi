package nwdi.blimbink.newalbarzanji

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nwdi.blimbink.newalbarzanji.adapter.AdapterCardTeks

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null // MediaPlayer untuk memutar audio
    private var isPlaying = false // Status pemutaran musik, false berarti tidak diputar

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail) // Mengatur layout aktivitas ini

        // Mendapatkan referensi dari elemen UI
        val titleTextView = findViewById<TextView>(R.id.detail_title)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_descriptions)
        val playButton = findViewById<ImageButton>(R.id.play_button)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_detail)

        setupToolbar(toolbar) // Mengatur Toolbar
        setupRecyclerView(recyclerView) // Mengatur RecyclerView
        setupMediaPlayer(playButton) // Mengatur MediaPlayer dan tombol play

        // Mendapatkan judul item dari intent dan memuat detail item
        val title = intent.getStringExtra("ITEM_TITLE")
        title?.let {
            loadDetail(it, titleTextView, recyclerView)
        }
    }

    // Mengatur Toolbar untuk aktivitas ini
    private fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar) // Mengatur Toolbar sebagai ActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Menampilkan tombol kembali di Toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressed() // Mengatur aksi ketika tombol kembali di klik
        }
    }

    // Mengatur RecyclerView dengan LinearLayoutManager
    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this) // Menetapkan layout manager untuk RecyclerView
    }

    // Mengatur MediaPlayer dan logika tombol play
    private fun setupMediaPlayer(playButton: ImageButton) {
        playButton.setOnClickListener {
            if (isPlaying) {
                mediaPlayer?.pause() // Menjeda pemutaran audio
                playButton.setImageResource(R.drawable.icon_play) // Mengubah ikon tombol ke play
            } else {
                mediaPlayer?.start() // Memulai pemutaran audio
                playButton.setImageResource(R.drawable.icon_pause) // Mengubah ikon tombol ke pause
            }
            isPlaying = !isPlaying // Toggle status pemutaran
        }
    }

    // Memuat detail item dari JSON dan menampilkan di UI
    private fun loadDetail(title: String, titleTextView: TextView, recyclerView: RecyclerView) {
        // Membaca file JSON dari assets
        val jsonString = assets.open("descriptions.json").bufferedReader().use { it.readText() }
        // Menguraikan JSON menjadi list DetailItem
        val detailItems: List<DetailItem> =
            Gson().fromJson(jsonString, object : TypeToken<List<DetailItem>>() {}.type)

        // Mencari item dengan judul yang cocok
        val selectedItem = detailItems.find { it.title == title }
        selectedItem?.let {
            titleTextView.text = it.title // Menampilkan judul di TextView
            // Menyiapkan adapter dengan data item
            val adapter = AdapterCardTeks(this, it.descriptionItems.map { item -> item.textOn to item.textOff })
            recyclerView.adapter = adapter // Menetapkan adapter ke RecyclerView
            prepareAudio(it.audioFileName) // Menyiapkan audio untuk diputar
        }
    }

    // Menyiapkan MediaPlayer untuk memutar audio
    private fun prepareAudio(audioFileName: String) {
        if (audioFileName.isNotEmpty()) {
            // Mendapatkan ID sumber daya audio dari nama file
            val audioResId = resources.getIdentifier(audioFileName, "raw", packageName)
            if (audioResId != 0) {
                mediaPlayer = MediaPlayer.create(this, audioResId) // Membuat MediaPlayer dengan audio sumber daya
            }
        }
    }

    // Membersihkan MediaPlayer saat aktivitas dihancurkan
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Melepaskan sumber daya MediaPlayer
        mediaPlayer = null
    }
}

// Data class untuk item deskripsi yang menampilkan teks
data class DescriptionItem(
    val textOn: String,
    val textOff: String
)

// Data class untuk detail item yang berisi judul, deskripsi, dan nama file audio
data class DetailItem(
    val title: String,
    val descriptionItems: List<DescriptionItem>,
    val audioFileName: String
)
