package com.rakamin.bankmandiri.newsapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.bankmandiri.newsapp.BuildConfig
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.model.Article
import com.rakamin.bankmandiri.newsapp.utils.GlideUtils
import com.rakamin.bankmandiri.newsapp.utils.NewsUtils
import com.rakamin.bankmandiri.newsapp.utils.getLogoUrlFromArticle
import getTimeAgo

class HomeAdapter(
    private val context: Context,
    private val loadMoreCallback: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var newsList = mutableListOf<Article>()
    private var isLoading = false

    companion object {
        private const val VIEW_TYPE_HEADER_BREAKING = 0
        private const val VIEW_TYPE_BREAKING = 1
        private const val VIEW_TYPE_HEADER_LATEST = 2
        private const val VIEW_TYPE_ITEM = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_HEADER_BREAKING
            1 -> VIEW_TYPE_BREAKING
            2 -> VIEW_TYPE_HEADER_LATEST
            else -> VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER_BREAKING, VIEW_TYPE_HEADER_LATEST -> {
                val view = inflater.inflate(R.layout.item_header, parent, false)
                HeaderViewHolder(view)
            }
            VIEW_TYPE_BREAKING -> {
                val view = inflater.inflate(R.layout.item_news_large, parent, false)
                BreakingNewsViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_news, parent, false)
                NewsViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (newsList.isEmpty()) 0 else newsList.size + 2 // Tambahkan 2 header hanya jika ada data
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.tvHeader.text = if (position == 0) "Breaking News" else "Latest News"
            }
            is BreakingNewsViewHolder -> {
                val article = newsList[0]

                holder.tvTitleLarge.text = article.title
                holder.tvTimeAgoLarge.text = getTimeAgo(article.publishedAt ?: "")
                holder.tvSourceURL.text = article.source.name
                GlideUtils.loadImageWithShimmer(context, article.urlToImage, holder.ivNewsImageLarge)
                GlideUtils.loadImageWithShimmer(context, getLogoUrlFromArticle(article.url.toString()), holder.ivLogoSource)
                holder.itemView.setOnClickListener { NewsUtils.openNewsDetail(context, article) }
            }
            is NewsViewHolder -> {
                val article = newsList[position - 2] // Offset karena ada 2 header
                holder.tvTitle.text = article.title
                holder.tvTimeAgo.text = getTimeAgo(article.publishedAt ?: "")
                holder.tvDescription.text = article.description ?: ""
                GlideUtils.loadImageWithShimmer(context, article.urlToImage, holder.ivNewsImage)
                holder.itemView.setOnClickListener { NewsUtils.openNewsDetail(context, article) }
            }
        }

        if (position == newsList.size - 1 && !isLoading) {
            loadMoreCallback()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newArticles: List<Article>) {
        if (newArticles.isEmpty()) return
        newsList.clear()
        newsList.addAll(newArticles)
        notifyDataSetChanged()
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHeader: TextView = view.findViewById(R.id.tvHeader)
    }

    class BreakingNewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivNewsImageLarge: ImageView = view.findViewById(R.id.ivNewsImageLarge)
        val ivLogoSource: ImageView = view.findViewById(R.id.ivLogoSource)
        val tvTitleLarge: TextView = view.findViewById(R.id.tvTitleLarge)
        val tvSourceURL: TextView = view.findViewById(R.id.tvSourceURL)
        val tvTimeAgoLarge: TextView = view.findViewById(R.id.tvTimeAgoLarge)
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivNewsImage: ImageView = view.findViewById(R.id.ivNewsImage)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvTimeAgo: TextView = view.findViewById(R.id.tvTimeAgo)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
    }
}


