package com.rakamin.bankmandiri.newsapp.utils

import android.content.Context
import android.content.Intent
import com.rakamin.bankmandiri.newsapp.data.local.NewsDatabase
import com.rakamin.bankmandiri.newsapp.data.local.NewsEntity
import com.rakamin.bankmandiri.newsapp.data.repository.SavedNewsRepository
import com.rakamin.bankmandiri.newsapp.model.Article
import com.rakamin.bankmandiri.newsapp.ui.detail.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object NewsUtils {

    fun openNewsDetail(context: Context, article: Article) {
        // Buka DetailActivity
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra("title", article.title)
            putExtra("content", article.content)
            putExtra("description", article.description)
            putExtra("url", article.url)
            putExtra("sourceName", article.source.name)
            putExtra("author", article.author)
            putExtra("urlToImage", article.urlToImage)
            putExtra("publishedAt", article.publishedAt)
        }
        context.startActivity(intent)

        saveNewsToDatabase(context, article)
    }

    private fun saveNewsToDatabase(context: Context, article: Article) {
        val db = NewsDatabase.getDatabase(context)
        val newsDao = db.newsDao()

        val savedNewsRepository = SavedNewsRepository(newsDao)

        CoroutineScope(Dispatchers.IO).launch {
            val existingNews = newsDao.getNewsByUrl(article.url)
            if (existingNews == null) {
                val newsEntity = NewsEntity(
                    title = article.title,
                    content = article.content ?: "",
                    url = article.url ?: "",
                    source = article.source.name ?: "",
                    author = article.author ?: "Unknown",
                    description = article.description ?: "",
                    urlToImage = article.urlToImage ?: "",
                    publishedAt = article.publishedAt ?: "",
                    highlight = "",
                    summary = "",
                    qna = "",
                    sentiment = ""
                )
                savedNewsRepository.insertNews(newsEntity)
            }
        }
    }
}
