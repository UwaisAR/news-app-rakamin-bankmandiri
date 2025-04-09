package com.rakamin.bankmandiri.newsapp.ui.spotlight

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.facebook.shimmer.ShimmerFrameLayout
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.data.local.NewsDatabase
import com.rakamin.bankmandiri.newsapp.network.RetrofitInstance
import com.rakamin.bankmandiri.newsapp.data.repository.NewsRepository
import com.rakamin.bankmandiri.newsapp.model.Article
import com.rakamin.bankmandiri.newsapp.utils.ZoomOutPageTransformer
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class SpotlightFragment : Fragment() {

    private var _viewPager: ViewPager2? = null
    private val viewPager get() = _viewPager!!

    private lateinit var adapter: SpotlightAdapter
    private val newsList = mutableListOf<Article>()

    private lateinit var shimmerLayout: ShimmerFrameLayout
    private var isLoading = false

    private val viewModel: SpotlightViewModel by viewModels {
        SpotlightViewModelFactory(
            NewsRepository(
                NewsDatabase.getDatabase(requireContext()).newsDao(),
                RetrofitInstance.api
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_spotlight, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _viewPager = view.findViewById(R.id.viewPager)
        shimmerLayout = view.findViewById(R.id.shimmerLayout)

        adapter = SpotlightAdapter(newsList, requireContext())
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        shimmerLayout.visibility = View.VISIBLE
        shimmerLayout.startShimmer()
        viewModel.fetchReelsNews()
        observeNews()
        setupInfiniteScroll()

        viewPager.setPageTransformer(ZoomOutPageTransformer())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeNews() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.newsList.collectLatest { articles ->
                val filteredNews = articles.filter { it.urlToImage != null }

                if (filteredNews.isNotEmpty()) {
                    newsList.clear()
                    newsList.addAll(filteredNews)
                    adapter.notifyDataSetChanged()

                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    isLoading = false
                }
            }
        }
    }


    private fun setupInfiniteScroll() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (!isLoading && position >= newsList.size - 2) {
                    isLoading = true
                    loadMoreNews()
                }
            }
        })
    }


    private fun loadMoreNews() {
        shimmerLayout.startShimmer()
        shimmerLayout.visibility = View.VISIBLE

        lifecycleScope.launch {
            viewModel.fetchReelsNews()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewPager = null
    }
}