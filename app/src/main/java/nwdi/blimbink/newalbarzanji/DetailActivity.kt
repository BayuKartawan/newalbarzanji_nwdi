package nwdi.blimbink.newalbarzanji

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nwdi.blimbink.newalbarzanji.adapter.DescriptionAdapter

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false // Status pemutaran musik

    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val titleTextView = findViewById<TextView>(R.id.detail_title)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_descriptions)
        val playButton = findViewById<ImageButton>(R.id.play_button)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get the title from the intent
        val title = intent.getStringExtra("ITEM_TITLE")

        // Load JSON from assets
        val jsonString = assets.open("descriptions.json").bufferedReader().use { it.readText() }
        val detailItems: List<DetailItem> =
            Gson().fromJson(jsonString, object : TypeToken<List<DetailItem>>() {}.type)

        // Find the selected item based on the title
        val selectedItem = detailItems.find { it.title == title }

        if (selectedItem != null) {
            // Set the title
            titleTextView.text = selectedItem.title

            // Set up and attach the adapter
            val descriptionAdapter = DescriptionAdapter(selectedItem.descriptionItems)
            recyclerView.adapter = descriptionAdapter

            // Prepare audio if available
            val audioFileName = selectedItem.audioFileName
            if (audioFileName.isNotEmpty()) {
                val audioResId = resources.getIdentifier(audioFileName, "raw", packageName)
                if (audioResId != 0) {
                    mediaPlayer = MediaPlayer.create(this, audioResId)
                    playButton.setOnClickListener {
                        if (isPlaying) {
                            // Pause the music
                            mediaPlayer?.pause()
                            playButton.setImageResource(R.drawable.icon_play) // Ubah ikon ke play
                        } else {
                            // Start the music
                            mediaPlayer?.start()
                            playButton.setImageResource(R.drawable.icon_pause) // Ubah ikon ke pause
                        }
                        isPlaying = !isPlaying // Toggle status pemutaran
                    }
                }
            }
        }

        // Navigasi Keluar Toolbar
        // Find the Toolbar by its ID
        val toolbar: Toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        // Enable the "up" button on the Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle the navigation click
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}

data class DescriptionItem(
    val textOn: String,
    val textOff: String
)

data class DetailItem(
    val title: String,
    val descriptionItems: List<DescriptionItem>,
    val audioFileName: String
)
