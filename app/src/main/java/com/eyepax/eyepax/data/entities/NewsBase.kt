package com.eyepax.eyepaxtest.data.entities

data class NewsBase(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)