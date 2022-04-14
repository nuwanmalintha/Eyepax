package com.eyepax.eyepax.data.entities

import com.eyepax.eyepax.data.entities.Article

data class NewsBase(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)