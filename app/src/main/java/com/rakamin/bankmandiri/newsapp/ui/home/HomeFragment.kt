package com.rakamin.bankmandiri.newsapp.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rakamin.bankmandiri.newsapp.BuildConfig
import com.rakamin.bankmandiri.newsapp.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recyclerview, container, false)

        recyclerView = view.findViewById(R.id.rvNewsList)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        homeAdapter = HomeAdapter(requireContext()) { viewModel.loadMoreNews() }
        recyclerView.adapter = homeAdapter

        observeNews()
        fetchNews()

        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        return view
    }

    private fun refreshData() {
        swipeRefreshLayout.isRefreshing = true
        viewModel.resetPagination()
        fetchNews()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun fetchNews() {
        viewModel.fetchNews("us", "general", BuildConfig.NEWS_API_KEY)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeNews() {
        lifecycleScope.launch {
            viewModel.newsList.collectLatest { articles ->
                if (articles.isNotEmpty()) {
                    homeAdapter.updateData(articles)
                }
            }
        }
    }


}
