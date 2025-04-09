package com.rakamin.bankmandiri.newsapp.ui.spotlight

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.model.Article
import com.rakamin.bankmandiri.newsapp.utils.GlideUtils
import com.rakamin.bankmandiri.newsapp.utils.NewsUtils

class SpotlightAdapter(
    private var newsList: MutableList<Article>,
    private val context: Context,
) : RecyclerView.Adapter<SpotlightAdapter.SpotlightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_spotlight, parent, false)
        return SpotlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpotlightViewHolder, position: Int) {
        val article = newsList[position]
        GlideUtils.loadImageWithShimmer(context, article.urlToImage, holder.imageView)

        holder.tvTitle.text = article.title
        holder.tvContent.text = article.content ?: "No Content"

        holder.itemView.setOnClickListener {
            NewsUtils.openNewsDetail(context, article)
        }
    }

    override fun getItemCount() = newsList.size

    class SpotlightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewNews)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvContent: TextView = itemView.findViewById(R.id.tvContent)
    }
}
