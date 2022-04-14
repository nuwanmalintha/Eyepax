package com.eyepax.eyepaxtest.data.remote

import com.eyepax.eyepaxtest.data.entities.NewsBase
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {
    @GET("everything")
    suspend fun getNewsSearch(
        @Query("q") searchText: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int
    ): Response<NewsBase>

    @GET("top-headlines")
    suspend fun getBreackingNewsWithFilter(
        @Query("country") country: String?,
        @Query("apiKey") apiKey: String,
        @Query("language") language: String?,
        @Query("category") category: String?,
        @Query("page") page: Int
    ): Response<NewsBase>
}