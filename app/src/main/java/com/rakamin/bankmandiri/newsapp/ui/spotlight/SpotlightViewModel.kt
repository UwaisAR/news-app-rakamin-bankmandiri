package com.rakamin.bankmandiri.newsapp.ui.spotlight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rakamin.bankmandiri.newsapp.data.repository.NewsRepository
import com.rakamin.bankmandiri.newsapp.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SpotlightViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _newsList = MutableStateFlow<List<Article>>(emptyList())
    val newsList: StateFlow<List<Article>> = _newsList

    private var currentPage = 1

    fun fetchReelsNews() {
        viewModelScope.launch {
            val currentArticles = _newsList.value
            val newArticles = repository.getRecommendedNews(currentPage, currentArticles)

            if (newArticles.isNotEmpty()) {
                val updatedList = (currentArticles + newArticles).distinctBy { it.url }
                _newsList.value = updatedList
                currentPage++
            }
        }
    }

}


class SpotlightViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpotlightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SpotlightViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

