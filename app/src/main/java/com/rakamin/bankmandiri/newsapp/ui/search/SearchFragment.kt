package com.rakamin.bankmandiri.newsapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.data.local.NewsDatabase
import com.rakamin.bankmandiri.newsapp.network.RetrofitInstance
import com.rakamin.bankmandiri.newsapp.data.repository.NewsRepository
import com.rakamin.bankmandiri.newsapp.ui.home.NewsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var etSearch: EditText
    private lateinit var btnClear: ImageButton
    private lateinit var btnSearch: Button
    private lateinit var ivFilter: ImageView
    private lateinit var layoutSpinners: LinearLayout
    private lateinit var spinnerCategory: Spinner
    private lateinit var spinnerLanguage: Spinner
    private lateinit var spinnerSortBy: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter

    private val viewModel: SearchViewModel by lazy {
        val repository = NewsRepository(
            NewsDatabase.getDatabase(requireContext()).newsDao(),
            RetrofitInstance.api
        )
        val factory = SearchViewModelFactory(repository)
        ViewModelProvider(this, factory)[SearchViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.apply {
            etSearch = findViewById(R.id.etSearch)
            btnClear = findViewById(R.id.btnClear)
            btnSearch = findViewById(R.id.btnSearch)
            ivFilter = findViewById(R.id.ivFilter)
            layoutSpinners = findViewById(R.id.layoutSpinners)
            spinnerCategory = findViewById(R.id.spinnerCategory)
            spinnerLanguage = findViewById(R.id.spinnerLanguage)
            spinnerSortBy = findViewById(R.id.spinnerSortBy)
            recyclerView = findViewById(R.id.recyclerView)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        newsAdapter = NewsAdapter(requireContext()) {
            if (!viewModel.isLoading) {
                viewModel.loadMoreNews()
            }
        }
        recyclerView.adapter = newsAdapter

        setupSpinners()
        setupSearchButton()
        setupClearButton()
        setupToggleSpinnerButton()

        lifecycleScope.launch {
            viewModel.newsList.collectLatest { articles ->
                newsAdapter.updateData(articles)
            }
        }

        return view
    }

    private fun setupSpinners() {
        val categories = listOf("Any", "Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")
        val languages = listOf("Any", "ar", "de", "en", "es", "fr", "he", "it", "nl", "no", "pt", "ru", "sv", "ud", "zh", "id")
        val sortByOptions = listOf("Relevancy", "Popularity", "PublishedAt")

        spinnerCategory.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        spinnerLanguage.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, languages)
        spinnerSortBy.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, sortByOptions)
    }

    private fun setupSearchButton() {
        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            val category = spinnerCategory.selectedItem.toString().lowercase()
            val language = spinnerLanguage.selectedItem.toString()
            val sortBy = spinnerSortBy.selectedItem.toString().lowercase()

            if (query.isBlank()) {
                Toast.makeText(requireContext(), "Enter keyword search", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.fetchSearchNews(query, category, language, sortBy)
            }
        }
    }

    private fun setupClearButton() {
        btnClear.setOnClickListener {
            etSearch.text.clear()
        }
    }

    private fun setupToggleSpinnerButton() {
        ivFilter.setOnClickListener {
            layoutSpinners.visibility = if (layoutSpinners.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
    }
}
