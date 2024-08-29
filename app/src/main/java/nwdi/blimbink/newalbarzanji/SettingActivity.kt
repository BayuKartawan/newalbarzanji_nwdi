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

    private lateinit var fontSizeTextOnSeekBar: SeekBar
    private lateinit var fontSizeTextOffSeekBar: SeekBar
    private lateinit var fontSizeTextOnValueTextView: TextView
    private lateinit var fontSizeTextOffValueTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // Initialize Views
        fontSizeTextOnSeekBar = findViewById(R.id.seekbar_font_size_text_on)
        fontSizeTextOffSeekBar = findViewById(R.id.seekbar_font_size_text_off)
        fontSizeTextOnValueTextView = findViewById(R.id.textview_font_size_valueon)
        fontSizeTextOffValueTextView = findViewById(R.id.textview_font_size_valueoff)

        // Set initial value of SeekBar and TextView
        fontSizeTextOnSeekBar.progress = sharedPreferences.getInt("FontSizeTextOn", 30)
        fontSizeTextOffSeekBar.progress = sharedPreferences.getInt("FontSizeTextOff", 16)
        fontSizeTextOnValueTextView.text = fontSizeTextOnSeekBar.progress.toString()
        fontSizeTextOffValueTextView.text = fontSizeTextOffSeekBar.progress.toString()

        // Set up SeekBar change listener
        setupSeekBarListener(fontSizeTextOnSeekBar, fontSizeTextOnValueTextView, "FontSizeTextOn")
        setupSeekBarListener(
            fontSizeTextOffSeekBar,
            fontSizeTextOffValueTextView,
            "FontSizeTextOff"
        )

        // Handle night mode switch
        val nightModeSwitch = findViewById<Switch>(R.id.switch1)
        nightModeSwitch.isChecked = isNightModeEnabled()
        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateNightMode(isChecked)
        }

        // Navigasi Keluar Toolbar
        // Find the Toolbar by its ID
        val toolbar: Toolbar = findViewById(R.id.toolbar_setelan)
        setSupportActionBar(toolbar)
        // Enable the "up" button on the Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Handle the navigation click
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
        val mode =
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
        sharedPreferences.edit().putBoolean("NightMode", isNightMode).apply()
        recreate()
    }
}
