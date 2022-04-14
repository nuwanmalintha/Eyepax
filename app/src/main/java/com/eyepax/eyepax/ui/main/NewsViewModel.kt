package com.eyepax.eyepaxtest.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eyepax.eyepaxtest.data.entities.NewsBase
import com.eyepax.eyepaxtest.data.repository.NewsRepository
import com.eyepax.eyepaxtest.utils.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val newsSearch = MutableLiveData<Resource<NewsBase>>()
    val breakingNewsWithFilter = MutableLiveData<Resource<NewsBase>>()

    fun loadNewsSearch(searchText: String, page: Int) = viewModelScope.launch {
        newsSearch.postValue(Resource.Loading())
        try {
            val response = newsRepository.loadSearchNews(searchText, page)
            if (response.isSuccessful) {
                response.body()?.let {
                    newsSearch.postValue(Resource.Success(it))
                }
            } else {
                newsSearch.postValue(Resource.Error(response.message()))
            }

        } catch (e: Exception) {
            newsSearch.postValue(Resource.Error(e.message.toString()))
        }

    }

    fun loadBreakingNewsWithFilter(
        country: String?,
        language: String,
        category: String?,
        page: Int
    ) =
        viewModelScope.launch {
            breakingNewsWithFilter.postValue(Resource.Loading())
            try {
                val response =
                    newsRepository.loadBreackingNewsWithFilter(country, language, category, page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        breakingNewsWithFilter.postValue(Resource.Success(it))
                    }
                } else {
                    breakingNewsWithFilter.postValue(Resource.Error(response.message()))
                }

            } catch (e: Exception) {
                breakingNewsWithFilter.postValue(Resource.Error(e.message.toString()))
            }
        }
}