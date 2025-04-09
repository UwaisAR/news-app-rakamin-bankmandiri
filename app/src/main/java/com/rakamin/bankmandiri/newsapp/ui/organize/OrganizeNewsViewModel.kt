package com.rakamin.bankmandiri.newsapp.ui.organize

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rakamin.bankmandiri.newsapp.data.local.NewsEntity
import com.rakamin.bankmandiri.newsapp.data.repository.SavedNewsRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class OrganizeNewsViewModel(private val repository: SavedNewsRepository) : ViewModel() {

    val savedNews = repository.getAllSavedNews()
        .map { it.sortedByDescending { news -> news.id } }
        .asLiveData()

    val recentNews = repository.getAllRecentNews()
        .map { it.sortedByDescending { news -> news.id } }
        .asLiveData()

    fun deleteNews(news: NewsEntity) {
        viewModelScope.launch {
            repository.deleteNews(news)
        }
    }
}

class OrganizeNewsViewModelFactory(private val repository: SavedNewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrganizeNewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OrganizeNewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
