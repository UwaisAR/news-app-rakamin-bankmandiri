package com.rakamin.bankmandiri.newsapp.data.repository

import com.rakamin.bankmandiri.newsapp.data.local.NewsDAO
import com.rakamin.bankmandiri.newsapp.data.local.NewsEntity
import kotlinx.coroutines.flow.Flow

class SavedNewsRepository(private val newsDao: NewsDAO) {

    fun getAllSavedNews(): Flow<List<NewsEntity>> = newsDao.getAllNewsSaved()
    fun getAllRecentNews(): Flow<List<NewsEntity>> = newsDao.getAllNews()

    suspend fun insertNews(news: NewsEntity) {
        newsDao.insertNews(news)
    }

    suspend fun deleteNews(news: NewsEntity) {
        newsDao.deleteSavedNews(news)
    }

    suspend fun getNewsByUrl(url: String?): NewsEntity? {
        return newsDao.getNewsByUrl(url)
    }

    suspend fun updateNewsByUrl(
        url: String,
        highlight: String? = null,
        summary: String? = null,
        qna: String? = null,
        sentiment: String? = null,
        saved: Boolean? = null
    ) {
        newsDao.updateNewsByUrl(url, highlight, summary, qna, sentiment, saved)
    }


}
