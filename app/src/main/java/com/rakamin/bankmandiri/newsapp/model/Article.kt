package com.rakamin.bankmandiri.newsapp.model

data class Article(
    val title: String,
    val description: String?,
    val content: String?,
    val url: String?,
    val source: Source,
    val author: String?,
    val urlToImage: String?,
    val publishedAt: String?
)

data class Source(
    val id: String?,
    val name: String?
)