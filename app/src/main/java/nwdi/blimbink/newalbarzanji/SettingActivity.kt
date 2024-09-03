package nwdi.blimbink.newalbarzanji

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar

@Suppress("DEPRECATION")
class SettingActivity : AppCompatActivity() {

    private lateinit var fontSizeLatinSeekBar: SeekBar
    private lateinit var fontSizeArabicSeekBar: SeekBar
    private lateinit var fontSizeLatinValueTextView: TextView
    private lateinit var fontSizeArabicValueTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("UseSwitchCompatOrMaterialCode", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // Initialize Views
        fontSizeLatinSeekBar = findViewById(R.id.seekbar_fontsize_latin)
        fontSizeArabicSeekBar = findViewById(R.id.seekbar_fontsize_arab)
        fontSizeLatinValueTextView = findViewById(R.id.textview_fontsize_latin)
        fontSizeArabicValueTextView = findViewById(R.id.textview_fontsize_arab)

        // Set initial value of SeekBar and TextView
        fontSizeLatinSeekBar.progress = sharedPreferences.getInt("FontSizeLatin", 16)
        fontSizeArabicSeekBar.progress = sharedPreferences.getInt("FontSizeArabic", 24)
        fontSizeLatinValueTextView.text = fontSizeLatinSeekBar.progress.toString()
        fontSizeArabicValueTextView.text = fontSizeArabicSeekBar.progress.toString()

        // Set up SeekBar change listener
        setupSeekBarListener(fontSizeLatinSeekBar, fontSizeLatinValueTextView, "FontSizeLatin")
        setupSeekBarListener(fontSizeArabicSeekBar, fontSizeArabicValueTextView, "FontSizeArabic")

        // Handle night mode switch
        val nightModeSwitch = findViewById<Switch>(R.id.switch1)
        nightModeSwitch.isChecked = isNightModeEnabled()
        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateNightMode(isChecked)
        }

        // Navigasi Keluar Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_setelan)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupSeekBarListener(
        seekBar: SeekBar,
        valueTextView: TextView,
        preferenceKey: String
    ) {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                valueTextView.text = progress.toString()
                saveFontSize(preferenceKey, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Optional: Add code here if needed when tracking starts
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Optional: Add code here if needed when tracking stops
            }
        })
    }

    private fun saveFontSize(key: String, size: Int) {
        sharedPreferences.edit().putInt(key, size).apply()
    }

    private fun isNightModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun updateNightMode(isNightMode: Boolean) {
        val mode = if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
        sharedPreferences.edit().putBoolean("NightMode", isNightMode).apply()
        recreate()
    }
}
