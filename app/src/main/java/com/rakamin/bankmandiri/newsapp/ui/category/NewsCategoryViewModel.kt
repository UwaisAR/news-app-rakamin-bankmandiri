package com.rakamin.bankmandiri.newsapp.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakamin.bankmandiri.newsapp.BuildConfig
import com.rakamin.bankmandiri.newsapp.network.RetrofitInstance
import com.rakamin.bankmandiri.newsapp.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsCategoryViewModel : ViewModel() {

    private val _newsList = MutableLiveData<List<Article>>()
    val newsList: LiveData<List<Article>> get() = _newsList

    private var currentPage = 1
    private var currentCategory: String = "general"
    private var isLoading = false

    fun fetchNews(category: String) {
        currentCategory = category
        currentPage = 1
        loadNews(reset = true)
    }

    fun loadMoreNews() {
        if (isLoading) return
        currentPage++
        loadNews(reset = false)
    }

    private fun loadNews(reset: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            try {
                val response = RetrofitInstance.api.getTopHeadlinesInfinite(
                    country = "us",
                    category = currentCategory,
                    page = currentPage,
                    pageSize = 10,
                    apiKey = BuildConfig.NEWS_API_KEY
                )
                val newArticles = response.articles ?: emptyList()
                if (reset) {
                    _newsList.postValue(newArticles)
                } else {
                    val currentArticles = _newsList.value ?: emptyList()
                    _newsList.postValue(currentArticles + newArticles)
                }
            } catch (e: Exception) {
                // Error handling
            } finally {
                isLoading = false
            }
        }
    }
}

