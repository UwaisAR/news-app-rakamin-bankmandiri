package com.rakamin.bankmandiri.newsapp.utils

import android.view.View
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.rakamin.bankmandiri.newsapp.R
import kotlin.math.absoluteValue

class ZoomOutPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val imageView = page.findViewById<ImageView>(R.id.imageViewNews) // Ambil ImageView dari halaman

        imageView?.apply {
            val scaleFactor = 1.1f.coerceAtMost(1.0f + (0.1f * (1 - position.absoluteValue)))

            animate()
                .scaleX(scaleFactor)
                .scaleY(scaleFactor)
                .setDuration(3000) // Animasi lebih smooth
                .start()
        }
    }
}
