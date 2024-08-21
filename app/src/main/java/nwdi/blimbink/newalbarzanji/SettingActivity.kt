package nwdi.blimbink.newalbarzanji

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingActivity : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        // Mengambil referensi ke Switch dari layout
        val switch1 = findViewById<Switch>(R.id.switch1)

        // Mengecek mode saat ini (malam/siang) dan mengatur posisi switch
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        switch1.isChecked = currentNightMode == Configuration.UI_MODE_NIGHT_YES

        // Mengambil SharedPreferences untuk menyimpan preferensi pengguna
        val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Listener untuk mengatur mode siang/malam
        switch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Mengaktifkan mode malam
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putBoolean("NightMode", true)
            } else {
                // Mengaktifkan mode siang
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putBoolean("NightMode", false)
            }
            editor.apply()
            // Restart Activity untuk menerapkan perubahan
            recreate()
        }
    }
}
