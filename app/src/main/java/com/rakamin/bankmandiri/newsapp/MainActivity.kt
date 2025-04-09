package com.rakamin.bankmandiri.newsapp

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rakamin.bankmandiri.newsapp.ui.category.CategoryFragment
import com.rakamin.bankmandiri.newsapp.ui.home.HomeFragment
import com.rakamin.bankmandiri.newsapp.ui.spotlight.SpotlightFragment
import com.rakamin.bankmandiri.newsapp.ui.organize.OrganizeNewsFragment
import com.rakamin.bankmandiri.newsapp.ui.search.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        if (savedInstanceState == null) {
            loadFragment(OrganizeNewsFragment())
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_category -> CategoryFragment()
                R.id.nav_reels -> SpotlightFragment()
                R.id.nav_search -> SearchFragment()
                R.id.nav_saved -> OrganizeNewsFragment()
                else -> null
            }
            fragment?.let {
                loadFragment(it)
                true
            } ?: false
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (bottomNavigationView.selectedItemId != R.id.nav_home) {
                    bottomNavigationView.selectedItemId = R.id.nav_home
                } else {
                    finish()
                }
            }
        })
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .commit()
    }
}