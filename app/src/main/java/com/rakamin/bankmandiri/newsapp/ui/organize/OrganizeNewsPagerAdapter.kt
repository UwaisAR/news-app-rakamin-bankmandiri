package com.rakamin.bankmandiri.newsapp.ui.organize

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrganizeNewsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OrganizeListFragment.newInstance(OrganizeListFragment.TYPE_SAVED)
            1 -> OrganizeListFragment.newInstance(OrganizeListFragment.TYPE_RECENT)
            else -> throw IllegalArgumentException("Invalid tab position")
        }
    }
}