package com.rakamin.bankmandiri.newsapp.ui.webview

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.rakamin.bankmandiri.newsapp.R
import com.rakamin.bankmandiri.newsapp.data.local.NewsDatabase
import com.rakamin.bankmandiri.newsapp.data.repository.SavedNewsRepository
import com.rakamin.bankmandiri.newsapp.utils.GeminiAIHelper
import kotlinx.coroutines.launch

class WebViewActivity : AppCompatActivity() {

    private lateinit var loadingText: TextView
    private lateinit var webView: WebView
    private lateinit var btnBack: ImageButton
    private lateinit var btnNext: ImageButton
    private lateinit var btnSummarize: ImageButton
    private lateinit var btnShare: ImageButton
    private lateinit var btnOpenBrowser: ImageButton
    private lateinit var tvUrl: TextView
    private lateinit var btnClose: ImageButton
    private lateinit var btnReload: ImageButton
    private lateinit var loadingAnimation: LottieAnimationView
    private lateinit var loadingBackground: View
    private var currentUrl: String = ""
    private var htmlContent: String? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val db = NewsDatabase.getDatabase(this)
        val savedNewsRepository = SavedNewsRepository(db.newsDao())

        val initialUrl = intent.getStringExtra("url") ?: ""

        loadingText = findViewById(R.id.loadingText)
        loadingAnimation = findViewById(R.id.loadingAnimation)
        loadingBackground = findViewById(R.id.loadingBackground)
        webView = findViewById(R.id.webView)
        btnBack = findViewById(R.id.btnBack)
        btnNext = findViewById(R.id.btnNext)
        btnSummarize = findViewById(R.id.btnSummarize)
        btnShare = findViewById(R.id.btnShare)
        btnOpenBrowser = findViewById(R.id.btnOpenBrowser)
        tvUrl = findViewById(R.id.tvUrl)
        btnClose = findViewById(R.id.btnClose)
        btnReload = findViewById(R.id.btnReload)

        tvUrl.text = initialUrl
        currentUrl = initialUrl

        btnClose.setOnClickListener {
            finish()
        }
        btnReload.setOnClickListener { webView.reload() }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (url != null) {
                    currentUrl = url
                    tvUrl.text = url
                }
                Toast.makeText(applicationContext, "The page has successfully loaded", Toast.LENGTH_SHORT).show()
                webView.evaluateJavascript(
                    "(function() { return document.body.innerText; })();"
                ) { content ->
                    htmlContent = content
                }
            }
        }

        webView.settings.javaScriptEnabled = true
        if (initialUrl.isNotEmpty()) {
            webView.loadUrl(initialUrl)
        }

        btnBack.setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
        }

        btnNext.setOnClickListener {
            if (webView.canGoForward()) webView.goForward()
        }

        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, currentUrl)
            }
            startActivity(Intent.createChooser(shareIntent, "Share"))
        }

        btnOpenBrowser.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentUrl))
            startActivity(browserIntent)
        }

        btnSummarize.setOnClickListener {
            if (currentUrl != initialUrl) {
                Toast.makeText(applicationContext, "Unable to summarize links that are not from the original source", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (htmlContent.isNullOrBlank()) {
                Toast.makeText(applicationContext, "News content has not loaded, please try again later", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showLoading(true)

            lifecycleScope.launch {
                val content = htmlContent ?: return@launch
                val highlight = GeminiAIHelper.extractHighlight(content)
                val summary = GeminiAIHelper.summarizeNews(content)
                val qna = GeminiAIHelper.generateQnA(content)
                val sentiment = GeminiAIHelper.analyzeSentiment(content)

                val existingNews = savedNewsRepository.getNewsByUrl(currentUrl)

                val intent = Intent().apply {
                    putExtra("highlight", highlight)
                    putExtra("sentiment", sentiment)
                    putExtra("summary", summary)
                    putExtra("qna", qna)
                }

                setResult(RESULT_OK, intent)

                showLoading(false)

                if (existingNews != null) {
                    savedNewsRepository.updateNewsByUrl(currentUrl, highlight, summary, qna, sentiment, null)
                    Toast.makeText(applicationContext, "The summary is ready, navigate back to continue.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            loadingAnimation.visibility = View.VISIBLE
            loadingText.visibility = View.VISIBLE
            loadingBackground.visibility = View.VISIBLE
            loadingAnimation.playAnimation()
            animateLoadingText()

            webView.setOnTouchListener { _, _ -> true }

            btnBack.isEnabled = false
            btnNext.isEnabled = false
            btnSummarize.isEnabled = false
            btnShare.isEnabled = false
            btnOpenBrowser.isEnabled = false
            btnClose.isEnabled = false
            btnReload.isEnabled = false

        } else {
            loadingAnimation.cancelAnimation()
            loadingAnimation.visibility = View.GONE
            loadingText.visibility = View.GONE
            loadingBackground.visibility = View.GONE

            webView.setOnTouchListener(null)

            btnBack.isEnabled = true
            btnNext.isEnabled = true
            btnSummarize.isEnabled = true
            btnShare.isEnabled = true
            btnOpenBrowser.isEnabled = true
            btnClose.isEnabled = true
            btnReload.isEnabled = true
        }
    }

    private fun animateLoadingText() {
        val fadeAnim = ObjectAnimator.ofFloat(loadingText, "alpha", 0f, 1f).apply {
            duration = 1000
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }
        fadeAnim.start()
    }

}
