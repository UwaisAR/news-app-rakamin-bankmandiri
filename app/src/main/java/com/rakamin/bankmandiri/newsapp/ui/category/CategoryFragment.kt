    package com.rakamin.bankmandiri.newsapp.ui.category

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.viewpager2.widget.ViewPager2
    import com.google.android.material.tabs.TabLayout
    import com.google.android.material.tabs.TabLayoutMediator
    import com.rakamin.bankmandiri.newsapp.R

    class CategoryFragment : Fragment() {

        private lateinit var tabLayout: TabLayout
        private lateinit var viewPager: ViewPager2

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_tablayout, container, false)

            tabLayout = view.findViewById(R.id.tabLayout)
            viewPager = view.findViewById(R.id.viewPager)

            val adapter = CategoryPagerAdapter(this)
            viewPager.adapter = adapter

            val categories = listOf("Business", "Entertainment", "General", "Health", "Science", "Sports", "Technology")
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = categories[position]
            }.attach()

            return view
        }
    }
