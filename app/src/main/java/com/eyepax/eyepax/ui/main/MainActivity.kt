package com.eyepax.eyepax.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eyepax.eyepax.data.entities.Article
import com.eyepax.eyepax.data.entities.NewsBase
import com.eyepax.eyepax.data.remote.RetrofitClient
import com.eyepax.eyepax.data.repository.NewsRepository
import com.eyepax.eyepax.databinding.ActivityMainBinding
import com.eyepax.eyepax.ui.base.BaseActivity
import com.eyepax.eyepax.ui.newsdetails.NewsDetailsActivity
import com.eyepax.eyepax.ui.search.CategoryRecyclerAdapter
import com.eyepax.eyepax.ui.search.SearchActivity
import com.eyepax.eyepax.ui.search.SearchResultsRecyclerAdapter
import com.eyepax.eyepax.utils.*

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: CategoryRecyclerAdapter
    var country: String? = null
    var category: String? = null
    var language: String = "en"
    var page: Int = 1
    val list = mutableListOf<Article>()
    lateinit var brackingAdapter: BreakingNewsRecyclerAdapter
    lateinit var searchAdapter: SearchResultsRecyclerAdapter
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        initValue()
        addCategoryDataToRecyclerview()
        buttonAction()
        loadBreackingNews()
        loadTopNews()
        observers()


    }



    private fun loadBreackingNews() {
        viewModel.loadBreakingNewsWithFilter(country, language, category, page)
    }

    private fun loadTopNews() {
        viewModel.loadBreakingNewsWithFilter(country, language, category, page)
    }

    private fun observers() {
        viewModel.breakingNewsWithFilter.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        addSearchDataToRecyclerview(it)
                        addTopDataToRecyclerview(it)
                        hideLoading()
                    }
                }
                is Resource.Error -> {
                    hideLoading()
                }
                is Resource.Loading -> {
                    showLoading()
                }
            }
        }
    }

    private fun buttonAction() {
        binding.txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.txtSearch.text.toString().isNotEmpty()) {
                    searchNews(binding.txtSearch.text.toString(), true)
                    true
                }
            }
            false
        }

        binding.linearLayoutSeeAll.setOnClickListener {
            searchNews(binding.txtSearch.text.toString(), false)
        }
    }

    private fun showLoading() {
        binding.lottieAnimation.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.lottieAnimation.visibility = View.GONE
    }

    private fun addSearchDataToRecyclerview(data: NewsBase) {

        list.addAll(data.articles)


        var linearLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewBrakingNews.layoutManager = linearLayoutManager
        brackingAdapter =
            BreakingNewsRecyclerAdapter(list, this)
        binding.recyclerviewBrakingNews.adapter = brackingAdapter
        brackingAdapter.onItemClick = {
            goToNewsDetailsPage(it)
        }

    }

    private fun addTopDataToRecyclerview(data: NewsBase) {

        list.addAll(data.articles)
        var linearLayoutManager =
            LinearLayoutManager(this@MainActivity)
        binding.recyclerviewSearch.layoutManager = linearLayoutManager
        searchAdapter =
            SearchResultsRecyclerAdapter(list, this)
        binding.recyclerviewSearch.adapter = searchAdapter
        searchAdapter.onItemClick = {
            goToNewsDetailsPage(it)
        }

    }

    private fun goToNewsDetailsPage(article: Article) {
        val intent = Intent(this@MainActivity, NewsDetailsActivity::class.java)
        intent.putExtra(BUNDLE_TITLE, article.title)
        intent.putExtra(BUNDLE_DATE, MainUtility.geStringDateFormatted(article.publishedAt))
        intent.putExtra(BUNDLE_AUTHOR, article.author)
        intent.putExtra(BUNDLE_DESCRIPTION, article.description)
        intent.putExtra(BUNDLE_IMAGE, article.urlToImage)
        startActivity(intent)
    }

    private fun searchNews(text: String, isSearch: Boolean) {
        val intent = Intent(this@MainActivity, SearchActivity::class.java)
        intent.putExtra(BUNDLE_SEARCH_TEXT, text)
        intent.putExtra(BUNDLE_IS_SEARCH, isSearch)
        startActivity(intent)
    }

    private fun addCategoryDataToRecyclerview() {

        var linearLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewCategory.layoutManager = linearLayoutManager
        adapter = CategoryRecyclerAdapter(this, CATEGORY_LIST)
        binding.recyclerviewCategory.adapter = adapter
        adapter.onItemClick = { s: String, i: Int ->
            adapter.onClickDesignChange(i)
            category = s
            viewModel.loadBreakingNewsWithFilter(country, language, category, page)
        }
    }

    private fun initValue() {
        val repository = NewsRepository(RetrofitClient.getClient())
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, newsViewModelProviderFactory)[NewsViewModel::class.java]


        viewModel.newsSearch.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        Log.e(TAG, "initValue: " + it.articles[0].description)
                    }
                }
                is Resource.Error -> {
                    Log.e(TAG, "initValue: error")
                }
                is Resource.Loading -> {
                    Log.e(TAG, "initValue: loading")
                }
            }
        }
    }
}