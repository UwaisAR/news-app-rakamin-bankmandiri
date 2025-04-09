package com.rakamin.bankmandiri.newsapp.ui.organize

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.data.local.NewsEntity
import com.rakamin.bankmandiri.newsapp.ui.detail.DetailActivity
import com.rakamin.bankmandiri.newsapp.utils.GlideUtils
import getTimeAgo

class OrganizeNewsAdapter(
    private var newsList: List<NewsEntity>,
    private val context: Context,
    private val onDeleteClick: (NewsEntity) -> Unit
) : RecyclerView.Adapter<OrganizeNewsAdapter.OrganizeNewsViewHolder>() {

    class OrganizeNewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSummarized: TextView = view.findViewById(R.id.tvSummarized)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvTimeAgo: TextView = view.findViewById(R.id.tvTimeAgo)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivNewsImage: ImageView = view.findViewById(R.id.ivNewsImage)
        val btnDelete: ImageView = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizeNewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_news, parent, false)
        return OrganizeNewsViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: OrganizeNewsViewHolder, position: Int) {
        val news = newsList[position]

        holder.tvTitle.text = news.title
        holder.tvTimeAgo.text = getTimeAgo(news.publishedAt ?: "")
        holder.tvDescription.text = news.description ?: "No description available"

        if (news.highlight.isNullOrEmpty()) {
            holder.tvSummarized.visibility = View.GONE
        } else {
            holder.tvSummarized.visibility = View.VISIBLE
        }

        GlideUtils.loadImageWithShimmer(context, news.urlToImage, holder.ivNewsImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("title", news.title)
                putExtra("content", news.content)
                putExtra("description", news.description)
                putExtra("url", news.url)
                putExtra("sourceName", news.source)
                putExtra("author", news.author)
                putExtra("urlToImage", news.urlToImage)
                putExtra("publishedAt", news.publishedAt)
                putExtra("highlight", news.highlight)
                putExtra("summary", news.summary)
                putExtra("qna", news.qna)
                putExtra("sentiment", news.sentiment)
                putExtra("saved", news.saved)
            }
            val isSaved = news.saved
            Log.d("NewsDebug", "Item clicked: title=${news.title}, saved=$isSaved")
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(news)
        }
    }

    override fun getItemCount(): Int = newsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newNewsList: List<NewsEntity>) {
        newsList = newNewsList
        notifyDataSetChanged()
    }
}
