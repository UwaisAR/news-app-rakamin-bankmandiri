package com.rakamin.bankmandiri.newsapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.rakamin.bankmandiri.newsapp.R

object GlideUtils {

    fun loadImageWithShimmer(context: Context, imageUrl: String?, imageView: ImageView) {

        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(0xE0E0E0)
            .setHighlightColor(0xF5F5F5)
            .setDuration(1000)
            .build()

        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }

        Glide.with(context)
            .load(imageUrl)
            .thumbnail(Glide.with(context).load(shimmerDrawable))
            .apply(
                RequestOptions()
                    .placeholder(shimmerDrawable)
                    .error(R.drawable.error_placeholder)
            )
            .into(imageView)
    }
}
