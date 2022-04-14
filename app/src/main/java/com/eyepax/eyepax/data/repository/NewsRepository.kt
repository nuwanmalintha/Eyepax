package com.eyepax.eyepaxtest.data.repository

import com.eyepax.eyepaxtest.data.remote.NewsApiInterface
import com.eyepax.eyepaxtest.utils.API_KEY

class NewsRepository(
    private val apiService: NewsApiInterface
) {
    suspend fun loadSearchNews(
        searchText: String,
        page: Int
    ) = apiService.getNewsSearch(searchText, API_KEY, page)

    suspend fun loadBreackingNewsWithFilter(
        country: String?,
        language: String?,
        category: String?,
        page: Int
    ) = apiService.getBreackingNewsWithFilter(country, API_KEY, language, category, page)
}