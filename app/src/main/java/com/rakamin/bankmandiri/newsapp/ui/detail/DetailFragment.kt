package com.rakamin.bankmandiri.newsapp.ui.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.utils.GlideUtils
import com.rakamin.bankmandiri.newsapp.utils.getLogoUrlFromArticle
import formatPublishedDate

class DetailFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val tvSource: TextView = view.findViewById(R.id.tvSource)
        val ivLogoSource: ImageView = view.findViewById(R.id.ivLogoSource)
        val tvPublishedAt: TextView = view.findViewById(R.id.tvPublishedAt)
        val tvContent: TextView = view.findViewById(R.id.tvContent)

        Log.d("test", "${arguments?.getString("url")}")

        tvTitle.text = arguments?.getString("title")
        tvAuthor.text = "Author: ${arguments?.getString("author")}"
        tvSource.text = "${arguments?.getString("source")}"
        GlideUtils.loadImageWithShimmer(requireContext(), getLogoUrlFromArticle("${arguments?.getString("url")}"), ivLogoSource)
        tvPublishedAt.text = "Published at: ${formatPublishedDate(arguments?.getString("publishedAt"))}"
        tvContent.text = arguments?.getString("content")
        return view
    }

    companion object {
        fun newInstance(
            title: String,
            author: String,
            source: String,
            description: String,
            publishedAt: String,
            content: String,
            url: String
        ) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("author", author)
                    putString("source", source)
                    putString("description", description)
                    putString("publishedAt", publishedAt)
                    putString("content", content)
                    putString("url", url)
                }
            }
    }
}

