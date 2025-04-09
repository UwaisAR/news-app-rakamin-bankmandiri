package com.rakamin.bankmandiri.newsapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailPagerAdapter(
    activity: AppCompatActivity,
    private val title: String,
    private val author: String,
    private val source: String,
    private val description: String,
    private val publishedAt: String,
    private val content: String,
    private val highlight: String,
    private val summary: String,
    private val qna: String,
    private val sentiment: String,
    private val url: String
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DetailFragment.newInstance(
                title,
                author,
                source,
                description,
                publishedAt,
                content,
                url
            )
            1 -> SummaryAIFragment.newInstance(highlight, summary, qna, sentiment)
            else -> throw IllegalStateException("Invalid position $position")
        }
    }


}
