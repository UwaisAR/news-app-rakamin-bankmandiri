package com.rakamin.bankmandiri.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDAO {
    @Query("SELECT * FROM news_table")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news_table WHERE saved = 1")
    fun getAllNewsSaved(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)

    @Query("SELECT title FROM news_table ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomTitle(): String?

    @Query("SELECT * FROM news_table WHERE url = :url")
    suspend fun getNewsByUrl(url: String?): NewsEntity?

    @Query("""
        UPDATE news_table 
        SET 
            highlight = COALESCE(:highlight, highlight),
            summary = COALESCE(:summary, summary),
            qna = COALESCE(:qna, qna),
            sentiment = COALESCE(:sentiment, sentiment),
            saved = COALESCE(:saved, saved)
        WHERE url = :url
    """)
    suspend fun updateNewsByUrl(
        url: String,
        highlight: String?,
        summary: String?,
        qna: String?,
        sentiment: String?,
        saved: Boolean?
    )

    @Delete
    suspend fun deleteSavedNews(news: NewsEntity)
}
