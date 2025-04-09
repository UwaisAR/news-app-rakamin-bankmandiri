package com.rakamin.bankmandiri.newsapp.data.repository

import android.util.Log
import com.rakamin.bankmandiri.newsapp.BuildConfig
import com.rakamin.bankmandiri.newsapp.data.local.NewsDAO
import com.rakamin.bankmandiri.newsapp.network.NewsInterface
import com.rakamin.bankmandiri.newsapp.model.Article
import opennlp.tools.tokenize.SimpleTokenizer

class NewsRepository(
    private val newsDao: NewsDAO,
    private val apiService: NewsInterface
) {
    private suspend fun getKeywordWithOpenNLP(): String? {
        val title = newsDao.getRandomTitle()?.takeIf { it.isNotEmpty() } ?: return null
        val tokenizer = SimpleTokenizer.INSTANCE
        val tokens = tokenizer.tokenize(title)

        return tokens.filter { it.length > 3 }.randomOrNull()
    }

    suspend fun getRecommendedNews(page: Int, existingArticles: List<Article>): List<Article> {
        val keyword = getKeywordWithOpenNLP()
        Log.d("test", "keyword: $keyword")

        return if (!keyword.isNullOrEmpty()) {
            try {
                val response = apiService.recommendNews(
                    query = keyword,
                    language = "en",
                    sortBy = "relevancy",
                    page = page,
                    pageSize = 5,
                    apiKey = BuildConfig.NEWS_API_KEY
                )

                val newArticles = response.articles ?: emptyList()

                // Filter agar tidak ada berita yang sama berdasarkan URL
                newArticles.filter { newArticle ->
                    existingArticles.none { it.url == newArticle.url }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        } else {
            emptyList()
        }
    }


    suspend fun getSearchNews(query: String, category: String?, language: String?, sortBy: String, page: Int): List<Article> {
        return try {
            val response = apiService.searchNews(
                query = query,
                category = if (category == "any") null else category,
                language = if (language == "Any") null else language,
                sortBy = sortBy.lowercase(),
                page = page,
                pageSize = 10,
                apiKey = BuildConfig.NEWS_API_KEY
            )
            response.articles ?: emptyList()
        } catch (e: Exception) {
            Log.e("NewsRepository", "Error fetching news", e)
            emptyList()
        }
    }
}
