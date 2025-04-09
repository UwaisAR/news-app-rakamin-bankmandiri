package com.rakamin.bankmandiri.newsapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.ui.home.NewsAdapter

class NewsCategoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recyclerview, container, false)
        recyclerView = view.findViewById(R.id.rvNewsList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        newsAdapter = NewsAdapter(requireContext()) {
            viewModel.loadMoreNews()
        }

        recyclerView.adapter = newsAdapter

        val category = arguments?.getString(ARG_CATEGORY) ?: "general"
        viewModel = ViewModelProvider(this)[NewsCategoryViewModel::class.java]

        viewModel.fetchNews(category)
        viewModel.newsList.observe(viewLifecycleOwner) { articles ->
            newsAdapter.updateData(articles)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 3) {
                    viewModel.loadMoreNews()
                }
            }
        })

        return view
    }

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: String) = NewsCategoryFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_CATEGORY, category)
            }
        }
    }
}

