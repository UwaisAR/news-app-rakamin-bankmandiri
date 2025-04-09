package com.rakamin.bankmandiri.newsapp.ui.organize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.data.local.NewsDatabase
import com.rakamin.bankmandiri.newsapp.data.repository.SavedNewsRepository

class OrganizeListFragment : Fragment() {

    companion object {
        private const val ARG_TYPE = "news_type"
        const val TYPE_SAVED = "saved"
        const val TYPE_RECENT = "recent"

        fun newInstance(type: String): OrganizeListFragment {
            val fragment = OrganizeListFragment()
            val args = Bundle()
            args.putString(ARG_TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var organizeNewsAdapter: OrganizeNewsAdapter

    private val viewModel: OrganizeNewsViewModel by viewModels {
        OrganizeNewsViewModelFactory(
            SavedNewsRepository(
                NewsDatabase.getDatabase(requireContext()).newsDao()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_recyclerview, container, false)
        recyclerView = view.findViewById(R.id.rvNewsList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        organizeNewsAdapter = OrganizeNewsAdapter(emptyList(), requireContext()) { news ->
            viewModel.deleteNews(news)
            Toast.makeText(requireContext(), "News Deleted", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = organizeNewsAdapter

        val type = arguments?.getString(ARG_TYPE) ?: TYPE_SAVED
        if (type == TYPE_SAVED) {
            viewModel.savedNews.observe(viewLifecycleOwner) {
                organizeNewsAdapter.updateData(it)
            }
        } else {
            viewModel.recentNews.observe(viewLifecycleOwner) {
                organizeNewsAdapter.updateData(it)
            }
        }

        return view
    }
}

