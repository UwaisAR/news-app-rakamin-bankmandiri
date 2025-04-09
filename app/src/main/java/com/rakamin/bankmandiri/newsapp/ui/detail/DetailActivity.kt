package com.rakamin.bankmandiri.newsapp.ui.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.data.local.NewsDatabase
import com.rakamin.bankmandiri.newsapp.data.repository.SavedNewsRepository
import com.rakamin.bankmandiri.newsapp.ui.webview.WebViewActivity
import com.rakamin.bankmandiri.newsapp.utils.GlideUtils
import formatPublishedDate
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var database: NewsDatabase

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        database = NewsDatabase.getDatabase(this)
        val savedNewsRepository = SavedNewsRepository(database.newsDao())

        val url = intent.getStringExtra("url") ?: ""
        val btnOpenFullNews: Button = findViewById(R.id.btnOpenFullNews)
        val ivNewsImage: ImageView = findViewById(R.id.ivNewsImage)
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        val btnSave: ImageButton = findViewById(R.id.btnSave)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        lifecycleScope.launch {
            val existingNews = savedNewsRepository.getNewsByUrl(url)
            val stringAI = "Use the summarize feature in the open browser to generate the result"

            val title = existingNews?.title ?: intent.getStringExtra("title") ?: "Title not available"
            val author = existingNews?.author ?: intent.getStringExtra("author") ?: "Author unknown"
            val source = existingNews?.source ?: intent.getStringExtra("sourceName") ?: "Source not available"
            val description = existingNews?.description ?: intent.getStringExtra("description") ?: "Description not available"
            val urlToImage = existingNews?.urlToImage ?: intent.getStringExtra("urlToImage") ?: ""
            val publishedAt = existingNews?.publishedAt ?: formatPublishedDate(intent.getStringExtra("publishedAt").toString())
            val content = existingNews?.content ?: intent.getStringExtra("content")?.replace(Regex("\\[\\+\\d+\\s+chars]"), "") ?: "Konten tidak tersedia"
            val highlight = existingNews?.highlight ?: intent.getStringExtra("highlight") ?: stringAI
            val summary = existingNews?.summary ?: intent.getStringExtra("summary") ?: stringAI
            val qna = existingNews?.qna ?: intent.getStringExtra("qna") ?: stringAI
            val sentiment = existingNews?.sentiment ?: intent.getStringExtra("sentiment") ?: stringAI
            val urlNews = existingNews?.url ?: intent.getStringExtra("url") ?: ""
            var saved = existingNews?.saved ?: false


            fun updateButtonAppearance() {
                if (saved) {
                    btnSave.setImageResource(R.drawable.ic_savedfill)
                } else {
                    btnSave.setImageResource(R.drawable.ic_saved)
                }
            }

            updateButtonAppearance()

            btnSave.setOnClickListener {
                saved = !saved // toggle status
                updateButtonAppearance() // update icon langsung

                lifecycleScope.launch {
                    savedNewsRepository.updateNewsByUrl(
                        url = urlNews,
                        saved = saved
                    )
                    Toast.makeText(this@DetailActivity, if (saved) "Saved!" else "Removed from saved!", Toast.LENGTH_SHORT).show()

                }
            }

            if (urlToImage.isNotEmpty()) {
                GlideUtils.loadImageWithShimmer(this@DetailActivity, urlToImage, ivNewsImage)
            }

            val adapter = DetailPagerAdapter(this@DetailActivity, title, author, source, description, publishedAt, content, highlight, summary, qna, sentiment, urlNews)
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Detail"
                    1 -> "AI Summary"
                    else -> "Tab $position"
                }
            }.attach()
        }

        btnOpenFullNews.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("url", url)
            resultLauncher.launch(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(btnBack) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = statusBarHeight + 16
            view.layoutParams = params
            insets
        }

        ViewCompat.setOnApplyWindowInsetsListener(btnSave) { view, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            val params = view.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = statusBarHeight + 16
            view.layoutParams = params
            insets
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val highlight = data?.getStringExtra("highlight") ?: "Highlight not available"
            val summary = data?.getStringExtra("summary") ?: "Summary not available"
            val qna = data?.getStringExtra("qna") ?: "Q&A not available"
            val sentiment = data?.getStringExtra("sentiment") ?: "Sentiment not available"

            val fragment = supportFragmentManager.findFragmentByTag("f1") as? SummaryAIFragment
            fragment?.updateData(highlight, summary, qna, sentiment)
        }
    }
}
