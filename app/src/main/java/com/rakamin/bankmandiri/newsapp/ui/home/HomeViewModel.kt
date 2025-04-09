package com.rakamin.bankmandiri.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rakamin.bankmandiri.newsapp.BuildConfig
import com.rakamin.bankmandiri.newsapp.network.RetrofitInstance
import com.rakamin.bankmandiri.newsapp.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _newsList = MutableStateFlow<List<Article>>(emptyList())
    val newsList: StateFlow<List<Article>> = _newsList

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    fun fetchNews(country: String, category: String, apiKey: String) {
        if (isLoading || isLastPage) return
        isLoading = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getTopHeadlinesInfinite(country, category, apiKey, currentPage)
                response.articles?.let {
                    if (it.isNotEmpty()) {
                        _newsList.value += it
                        currentPage++
                    } else {
                        isLastPage = true
                    }
                }
            } catch (e: Exception) {
                // Handle error
            } finally {
                isLoading = false
            }
        }
    }

    fun loadMoreNews() {
        if (!isLoading && !isLastPage) {
            fetchNews("us", "general", BuildConfig.NEWS_API_KEY)
        }
    }

    fun resetPagination() {
        currentPage = 1
        isLastPage = false
        _newsList.value = emptyList()
    }
}


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
