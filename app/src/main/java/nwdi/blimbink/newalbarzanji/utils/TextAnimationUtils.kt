package nwdi.blimbink.newalbarzanji.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.widget.TextView

object TextAnimationUtils {

    fun showTextWithAnimation(textView: TextView, text: String, duration: Long, onAnimationEnd: () -> Unit) {
        val animator = ValueAnimator.ofInt(0, text.length)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            textView.text = text.substring(0, value)
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                onAnimationEnd()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start()
    }

    fun hideTextWithAnimation(textView: TextView, duration: Long) {
        val fullText = textView.text.toString()
        val animator = ValueAnimator.ofInt(fullText.length, 0)
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            textView.text = fullText.substring(0, value)
        }
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                textView.text = ""
                textView.visibility = android.view.View.GONE
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })
        animator.start()
    }

    fun calculateDuration(textLength: Int): Long {
        val baseDuration = 3000L // Base duration in milliseconds
        val maxLength = 100 // Maximum length for base duration
        return (baseDuration * (textLength / maxLength.toFloat())).toLong()
    }
}
