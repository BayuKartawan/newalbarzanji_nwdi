package nwdi.blimbink.newalbarzanji.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.widget.TextView

// Kelas utilitas untuk animasi teks
object TextAnimationUtils {

    // Menampilkan teks dengan animasi
    fun showTextWithAnimation(
        textView: TextView,
        text: String,
        duration: Long,
        onAnimationEnd: () -> Unit
    ) {
        // Membuat animator untuk menampilkan teks secara bertahap
        createValueAnimator(
            from = 0, // Mulai dari karakter ke-0
            to = text.length, // Sampai akhir teks
            duration = duration, // Durasi animasi
            updateListener = { value ->
                // Memperbarui teks yang ditampilkan pada setiap langkah animasi
                textView.text = text.substring(0, value)
            },
            onEnd = {
                // Menjalankan callback setelah animasi selesai
                onAnimationEnd()
            }
        ).start() // Memulai animasi
    }

    // Menyembunyikan teks dengan animasi
    fun hideTextWithAnimation(textView: TextView, duration: Long) {
        // Menyimpan teks penuh yang saat ini ditampilkan
        val fullText = textView.text.toString()
        // Membuat animator untuk menyembunyikan teks secara bertahap
        createValueAnimator(
            from = fullText.length, // Mulai dari akhir teks
            to = 0, // Sampai tidak ada teks
            duration = duration, // Durasi animasi
            updateListener = { value ->
                // Memperbarui teks yang ditampilkan pada setiap langkah animasi
                textView.text = fullText.substring(0, value)
            },
            onEnd = {
                // Menghapus teks dan menyembunyikan TextView setelah animasi selesai
                textView.text = ""
                textView.visibility = android.view.View.GONE
            }
        ).start() // Memulai animasi
    }

    // Membuat ValueAnimator dengan parameter tertentu
    private fun createValueAnimator(
        from: Int,
        to: Int,
        duration: Long,
        updateListener: (Int) -> Unit,
        onEnd: () -> Unit
    ): ValueAnimator {
        return ValueAnimator.ofInt(from, to).apply {
            this.duration = duration // Menetapkan durasi animasi
            addUpdateListener { animation ->
                val value = animation.animatedValue as Int
                // Memperbarui nilai teks yang ditampilkan
                updateListener(value)
            }
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    // Menjalankan callback setelah animasi selesai
                    onEnd()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        }
    }

    // Menghitung durasi animasi berdasarkan panjang teks
    fun calculateDuration(textLength: Int): Long {
        val baseDuration = 3000L // Durasi dasar dalam milidetik
        val maxLength = 100 // Panjang maksimum untuk durasi dasar
        // Menghitung durasi berdasarkan panjang teks
        return (baseDuration * (textLength / maxLength.toFloat())).toLong()
    }
}
