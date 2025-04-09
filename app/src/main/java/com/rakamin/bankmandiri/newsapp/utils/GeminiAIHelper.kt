package com.rakamin.bankmandiri.newsapp.utils

import android.annotation.SuppressLint
import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.rakamin.bankmandiri.newsapp.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GeminiAIHelper {
    private const val API_KEY = BuildConfig.GEMINI_API_KEY


    @SuppressLint("SecretInSource")
    private val model = GenerativeModel(
        modelName = "gemini-2.0-flash-lite",
        apiKey = API_KEY
    )

    // Extract news highlights
    suspend fun extractHighlight(content: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = model.generateContent(
                    "Main Highlights (Brief Outline) using this template:\n" +
                            "\t•\tMain topic:\n" +
                            "\t•\tPeople or parties involved:\n" +
                            "\t•\tTime of the event:\n" +
                            "\t•\tLocation of the event:\n" +
                            "\t•\tCause or background:\n" +
                            "\t•\tImpact or consequences:\n " +
                            "News content: $content"
                )
                response.text ?: "Highlights not available"
            } catch (e: Exception) {
                Log.e("GeminiAI", "Error extracting highlight: ${e.message}")
                "Failed to extract news highlights"
            }
        }
    }

    // Summarize news in 50-100 words
    suspend fun summarizeNews(content: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = model.generateContent(
                    "Summarize this news (50-100 words):\n$content"
                )
                response.text ?: "Summary not available"
            } catch (e: Exception) {
                Log.e("GeminiAI", "Error summarizing: ${e.message}")
                "Failed to summarize news"
            }
        }
    }

    // Generate QnA format
    suspend fun generateQnA(content: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = model.generateContent(
                    "QnA with brief answers using this template:\n" +
                            "•\tWhat happened?\n" +
                            "\t•\tWho was involved?\n" +
                            "\t•\tWhere and when did the event take place?\n" +
                            "\t•\tWhy did this happen?\n" +
                            "\t•\tWhat are the consequences?\n" +
                            "News content: $content"
                )
                response.text ?: "QnA not available"
            } catch (e: Exception) {
                Log.e("GeminiAI", "Error generating QnA: ${e.message}")
                "Failed to generate QnA"
            }
        }
    }

    // Analyze news sentiment
    suspend fun analyzeSentiment(content: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = model.generateContent(
                    "Determine the sentiment of this news in one word (positive, negative, neutral):\n$content"
                )
                response.text ?: "Sentiment analysis not available"
            } catch (e: Exception) {
                Log.e("GeminiAI", "Error analyzing sentiment: ${e.message}")
                "Failed to determine sentiment"
            }
        }
    }
}
