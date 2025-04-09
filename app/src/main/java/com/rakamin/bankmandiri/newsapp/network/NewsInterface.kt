package com.rakamin.bankmandiri.newsapp.network

import com.rakamin.bankmandiri.newsapp.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("category") category: String?,
        @Query("apiKey") apiKey: String
    ): NewsResponse

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("category") category: String?,
        @Query("language") language: String?,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): NewsResponse

    @GET("everything")
    suspend fun recommendNews(
        @Query("q") query: String,
        @Query("language") language: String?,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getTopHeadlinesInfinite(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int = 5
    ): NewsResponse

}
