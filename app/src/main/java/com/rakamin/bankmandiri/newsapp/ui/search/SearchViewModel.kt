package com.rakamin.bankmandiri.newsapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rakamin.bankmandiri.newsapp.data.repository.NewsRepository
import com.rakamin.bankmandiri.newsapp.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _newsList = MutableStateFlow<List<Article>>(emptyList())
    val newsList: StateFlow<List<Article>> = _newsList

    private var lastQuery: String = ""
    private var currentPage: Int = 1
    var isLoading: Boolean = false

    fun fetchSearchNews(query: String, category: String?, language: String?, sortBy: String) {
        if (query.isBlank()) {
            Log.e("SearchViewModel", "Error: query is empty, cannot fetch news.")
            return
        }

        lastQuery = query
        currentPage = 1
        _newsList.value = emptyList()

        viewModelScope.launch(Dispatchers.IO) {
            isLoading = true
            val articles = repository.getSearchNews(query, category, language, sortBy, currentPage)
            _newsList.value = articles
            isLoading = false
        }
    }

    fun loadMoreNews() {
        if (isLoading) return
        if (lastQuery.isBlank()) {
            Log.e("SearchViewModel", "Error: lastQuery is empty, cannot fetch more news.")
            return
        }

        isLoading = true
        currentPage++

        viewModelScope.launch(Dispatchers.IO) {
            val newArticles = repository.getSearchNews(lastQuery, null, null, "publishedAt", currentPage)
            _newsList.value += newArticles
            isLoading = false
        }
    }
}


class SearchViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
