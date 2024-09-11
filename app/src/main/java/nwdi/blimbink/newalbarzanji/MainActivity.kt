package nwdi.blimbink.newalbarzanji

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    // Deklarasi variabel untuk BottomNavigationView
    private lateinit var bottomNavigationView: BottomNavigationView

    // Fungsi untuk mengatur visibilitas tombol toggle navigation
    fun setBtnToggleNavVisibility(visibility: Int) {
        val btnToggleNav = findViewById<ImageButton>(R.id.btn_toggle_nav)
        btnToggleNav.visibility = visibility
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menginisialisasi BottomNavigationView dan tombol toggle
        bottomNavigationView = findViewById(R.id.nav_bawah)
        val toggleButton: ImageButton = findViewById(R.id.btn_toggle_nav)

        // Mengatur aksi klik pada tombol toggle
        toggleButton.setOnClickListener {
            // Menyembunyikan atau menampilkan BottomNavigationView saat tombol diklik
            if (bottomNavigationView.visibility == View.VISIBLE) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }

        // Mengatur listener untuk item yang dipilih di BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.NavDaftarIsi -> {
                    // Mengganti fragmen dengan DaftarIsiFragment
                    replaceFragment(DaftarIsiFragment())
                    true
                }

                R.id.NavBaca -> {
                    // Mengganti fragmen dengan BacaFragment
                    replaceFragment(BacaFragment())
                    true
                }

                R.id.NavLagu -> {
                    // Mengganti fragmen dengan LaguFragment
                    replaceFragment(LaguFragment())
                    true
                }

                R.id.NavTentang -> {
                    // Mengganti fragmen dengan TentangFragment
                    replaceFragment(TentangFragment())
                    true
                }

                else -> false
            }
        }
        // Menampilkan BacaFragment saat aplikasi pertama kali dibuka
        replaceFragment(BacaFragment())
    }

    // Fungsi untuk mengganti fragmen yang ditampilkan
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

    @Deprecated("Metode ini telah usang dan digantikan oleh OnBackPressedDispatcher.")
    override fun onBackPressed() {
        // Memeriksa fragmen saat ini dan menampilkan dialog konfirmasi keluar jika diperlukan
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_container)
        if (currentFragment is BacaFragment || currentFragment is DaftarIsiFragment || currentFragment is LaguFragment || currentFragment is TentangFragment) {
            showExitConfirmationDialog()
        } else {
            super.onBackPressed()
        }
    }

    // Menampilkan dialog konfirmasi saat ingin keluar dari aplikasi
    private fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("⚠\uFE0F Keluar Aplikasi")
        builder.setMessage(
            "Surga dan segala kenikmatannya disiapkan bagi orang-orang yang senantiasa bershalawat salam kepada Nabi Muhammad SAW\n\n" +
                    "Apakah Anda yakin ingin keluar dari aplikasi? \uD83D\uDE2D\uD83D\uDE2D\uD83D\uDE2D"
        )
        builder.setPositiveButton("Ya") { dialog, _ ->
            dialog.dismiss()
            super.onBackPressed() // Memanggil method onBackPressed dari superclass
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.dismiss() // Tutup dialog
        }
        builder.show()
    }
}
