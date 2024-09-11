package nwdi.blimbink.newalbarzanji

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint

@SuppressLint("CustomSplashScreen") // Menonaktifkan peringatan penggunaan SplashScreen kustom yang tidak disarankan
class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Mengaktifkan mode edge-to-edge untuk tampilan layar penuh
        setContentView(R.layout.activity_intro) // Mengatur layout activity_intro sebagai tampilan activity ini
        navigateToMainActivityAfterDelay() // Memanggil fungsi untuk berpindah ke MainActivity setelah penundaan
    }

    private fun navigateToMainActivityAfterDelay() {
        // Membuat Handler yang berjalan di main thread dan menunda eksekusi kode di dalam lambda
        Handler(Looper.getMainLooper()).postDelayed({
            goToMainActivity() // Memanggil fungsi untuk berpindah ke MainActivity
        }, DELAY_MS) // Menetapkan penundaan dalam milidetik sebelum eksekusi
    }

    private fun goToMainActivity() {
        // Membuat intent untuk berpindah ke MainActivity dan memulai activity baru
        startActivity(Intent(this, MainActivity::class.java))
        finish() // Menutup IntroActivity sehingga tidak bisa kembali ke layar ini
    }

    companion object {
        // Menyimpan nilai konstanta untuk durasi penundaan sebelum berpindah ke MainActivity
        private const val DELAY_MS = 3000L
    }
}
