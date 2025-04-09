package com.rakamin.bankmandiri.newsapp.ui.category

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val categories = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        return NewsCategoryFragment.newInstance(categories[position])
    }
}
