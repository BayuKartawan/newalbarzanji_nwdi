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

        // Inisialisasi SharedPreferences untuk menyimpan pengaturan aplikasi
        initializeSharedPreferences()

        // Inisialisasi elemen tampilan (views) dari layout
        initializeViews()

        // Set up listener untuk elemen tampilan agar dapat merespons interaksi pengguna
        initializeListeners()

        // Menyiapkan toolbar dengan tombol navigasi
        setupToolbar()
    }

    private fun initializeSharedPreferences() {
        // Mendapatkan SharedPreferences untuk menyimpan pengaturan aplikasi
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
    }

    private fun initializeViews() {
        // Menghubungkan elemen tampilan dengan ID dari layout XML
        fontSizeLatinSeekBar = findViewById(R.id.seekbar_fontsize_latin)
        fontSizeArabicSeekBar = findViewById(R.id.seekbar_fontsize_arab)
        fontSizeLatinValueTextView = findViewById(R.id.textview_fontsize_latin)
        fontSizeArabicValueTextView = findViewById(R.id.textview_fontsize_arab)

        // Mengatur nilai awal SeekBar dan TextView sesuai dengan pengaturan yang disimpan
        fontSizeLatinSeekBar.progress = sharedPreferences.getInt("FontSizeLatin", 16)
        fontSizeArabicSeekBar.progress = sharedPreferences.getInt("FontSizeArabic", 24)
        fontSizeLatinValueTextView.text = fontSizeLatinSeekBar.progress.toString()
        fontSizeArabicValueTextView.text = fontSizeArabicSeekBar.progress.toString()
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun initializeListeners() {
        // Mengatur listener untuk SeekBar untuk mengubah ukuran font
        setupSeekBarListener(fontSizeLatinSeekBar, fontSizeLatinValueTextView, "FontSizeLatin")
        setupSeekBarListener(fontSizeArabicSeekBar, fontSizeArabicValueTextView, "FontSizeArabic")

        // Mengatur listener untuk Switch untuk mengaktifkan/menonaktifkan mode malam
        val nightModeSwitch = findViewById<Switch>(R.id.switch1)
        nightModeSwitch.isChecked = isNightModeEnabled()
        nightModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            updateNightMode(isChecked)
        }
    }

    private fun setupSeekBarListener(
        seekBar: SeekBar,
        valueTextView: TextView,
        preferenceKey: String
    ) {
        // Mengatur listener untuk SeekBar yang menangani perubahan nilai
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Mengupdate TextView dengan nilai SeekBar yang baru
                valueTextView.text = progress.toString()
                // Menyimpan ukuran font ke SharedPreferences
                saveFontSize(preferenceKey, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Opsional: Menambahkan kode jika diperlukan saat tracking dimulai
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Opsional: Menambahkan kode jika diperlukan saat tracking berhenti
            }
        })
    }

    private fun saveFontSize(key: String, size: Int) {
        // Menyimpan ukuran font ke SharedPreferences
        sharedPreferences.edit().putInt(key, size).apply()
    }

    private fun isNightModeEnabled(): Boolean {
        // Mengecek apakah mode malam saat ini aktif
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun updateNightMode(isNightMode: Boolean) {
        // Mengubah mode malam sesuai dengan status yang dipilih
        val mode = if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(mode)
        // Menyimpan pengaturan mode malam ke SharedPreferences
        sharedPreferences.edit().putBoolean("NightMode", isNightMode).apply()
        // Memuat ulang Activity untuk menerapkan perubahan
        recreate()
    }

    private fun setupToolbar() {
        // Mengatur toolbar dan menambahkan tombol navigasi untuk kembali
        val toolbar: Toolbar = findViewById(R.id.toolbar_setelan)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}
