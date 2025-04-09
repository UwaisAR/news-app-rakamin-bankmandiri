package com.rakamin.bankmandiri.newsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val url: String?,
    val highlight: String?,
    val summary: String?,
    val qna: String?,
    val sentiment: String?,
    val content: String?,
    val source: String?,
    val author: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val saved: Boolean = false
)

