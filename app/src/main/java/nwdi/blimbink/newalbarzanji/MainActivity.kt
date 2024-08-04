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

    private lateinit var bottomNavigationView: BottomNavigationView

    fun setBtnToggleNavVisibility(visibility: Int) {
        val btnToggleNav = findViewById<ImageButton>(R.id.btn_toggle_nav)
        btnToggleNav.visibility = visibility
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.nav_bawah)
        val toggleButton: ImageButton = findViewById(R.id.btn_toggle_nav)

        toggleButton.setOnClickListener {
            if (bottomNavigationView.visibility == View.VISIBLE) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.NavDaftarIsi -> {
                    replaceFragment(DaftarIsiFragment())
                    true
                }

                R.id.NavBaca -> {
                    replaceFragment(BacaFragment())
                    true
                }

                R.id.NavLagu -> {
                    replaceFragment(LaguFragment())
                    true
                }

                R.id.NavTentang -> {
                    replaceFragment(TentangFragment())
                    true
                }

                else -> false
            }
        }
        replaceFragment(BacaFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.frame_container)
        if (currentFragment is BacaFragment || currentFragment is DaftarIsiFragment || currentFragment is LaguFragment || currentFragment is TentangFragment) {
            showExitConfirmationDialog()
        } else {
            super.onBackPressed()
        }
    }

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
