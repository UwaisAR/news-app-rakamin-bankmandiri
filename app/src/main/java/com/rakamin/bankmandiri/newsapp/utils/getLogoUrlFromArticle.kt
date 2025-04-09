package com.rakamin.bankmandiri.newsapp.utils

import com.rakamin.bankmandiri.newsapp.BuildConfig

fun getLogoUrlFromArticle(sourceUrl: String): String?{
    if (sourceUrl.isEmpty()) return null
    val domain = sourceUrl.substringAfter("://").substringBefore("/")
    val logoUrl = "https://img.logo.dev/$domain?token=${BuildConfig.LOGODEV_API_KEY}&retina=true"

    return logoUrl
}