package com.rakamin.bankmandiri.newsapp.ui.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rakamin.bankmandiri.newsapp.R
import io.noties.markwon.Markwon

class SummaryAIFragment : Fragment() {

    private lateinit var tvHighlight: TextView
    private lateinit var ivHighlightToggle: ImageView

    private lateinit var tvSummary: TextView
    private lateinit var ivSummaryToggle: ImageView

    private lateinit var tvQnA: TextView
    private lateinit var ivQnAToggle: ImageView

    private lateinit var tvSentiment: TextView
    private lateinit var ivSentimentToggle: ImageView

    private lateinit var markwon: Markwon

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_summary_ai, container, false)

        markwon = Markwon.create(requireContext())

        tvHighlight = view.findViewById(R.id.tvHighlight)
        ivHighlightToggle = view.findViewById(R.id.ivHighlightToggle)

        tvSummary = view.findViewById(R.id.tvSummary)
        ivSummaryToggle = view.findViewById(R.id.ivSummaryToggle)

        tvQnA = view.findViewById(R.id.tvQnA)
        ivQnAToggle = view.findViewById(R.id.ivQnAToggle)

        tvSentiment = view.findViewById(R.id.tvSentiment)
        ivSentimentToggle = view.findViewById(R.id.ivSentimentToggle)

        val highlight = arguments?.getString("highlight") ?: "Highlight tidak tersedia"
        val summary = arguments?.getString("summary") ?: "Ringkasan tidak tersedia"
        val qna = arguments?.getString("qna") ?: "Q&A tidak tersedia"
        val sentiment = arguments?.getString("sentiment") ?: "Sentimen tidak tersedia"

        markwon.setMarkdown(tvHighlight, highlight)
        markwon.setMarkdown(tvSummary, summary)
        markwon.setMarkdown(tvQnA, qna)
        markwon.setMarkdown(tvSentiment, "**Sentiment:** $sentiment")

        setupToggle(ivHighlightToggle, tvHighlight)
        setupToggle(ivSummaryToggle, tvSummary)
        setupToggle(ivQnAToggle, tvQnA)
        setupToggle(ivSentimentToggle, tvSentiment)

        return view
    }

    private fun setupToggle(toggleView: ImageView, contentView: TextView) {
        toggleView.setOnClickListener {
            val isExpanded = contentView.visibility == View.VISIBLE
            contentView.visibility = if (isExpanded) View.GONE else View.VISIBLE
            toggleView.setImageResource(if (isExpanded) R.drawable.ic_expand_more else R.drawable.ic_expand_less)
        }
    }

    companion object {
        fun newInstance(highlight: String, summary: String, qna: String, sentiment: String) =
            SummaryAIFragment().apply {
                arguments = Bundle().apply {
                    putString("highlight", highlight)
                    putString("summary", summary)
                    putString("qna", qna)
                    putString("sentiment", sentiment)
                }
            }
    }

    fun updateData(highlight: String, summary: String, qna: String, sentiment: String) {
        markwon.setMarkdown(tvHighlight, highlight)
        markwon.setMarkdown(tvSummary, summary)
        markwon.setMarkdown(tvQnA, qna)
        markwon.setMarkdown(tvSentiment, "**Sentiment:** $sentiment")
    }

}
