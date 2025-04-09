package com.rakamin.bankmandiri.newsapp.ui.home

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.model.Article
import com.rakamin.bankmandiri.newsapp.utils.GlideUtils
import com.rakamin.bankmandiri.newsapp.utils.NewsUtils
import getTimeAgo

class NewsAdapter(
    private val context: Context,
    private val loadMore: () -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var newsList = mutableListOf<Article>()
    private var isLoading = false

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_LOADING = 2
    }


    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivNewsImage: ImageView = view.findViewById(R.id.ivNewsImage)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvTimeAgo: TextView = view.findViewById(R.id.tvTimeAgo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NewsViewHolder) {
            val article = newsList[position]

            holder.tvTitle.text = article.title
            holder.tvTimeAgo.text = getTimeAgo(article.publishedAt ?: "")
            holder.tvDescription.text = article.description ?: "Description not available"

            GlideUtils.loadImageWithShimmer(context, article.urlToImage, holder.ivNewsImage)

            holder.itemView.setOnClickListener {
                NewsUtils.openNewsDetail(context, article)
            }

            if (position == newsList.size - 3 && !isLoading) {
                loadMore()
            }
        }
    }

    override fun getItemCount(): Int = newsList.size + if (isLoading) 1 else 0

    override fun getItemViewType(position: Int): Int {
        return if (position < newsList.size) VIEW_TYPE_ITEM else VIEW_TYPE_LOADING
    }

    fun updateData(newList: List<Article>) {
        val diffResult = DiffUtil.calculateDiff(NewsDiffCallback(newsList, newList))
        newsList.clear()
        newsList.addAll(newList)
        Handler(Looper.getMainLooper()).post {
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class NewsDiffCallback(private val oldList: List<Article>, private val newList: List<Article>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].url == newList[newItemPosition].url
        }
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
