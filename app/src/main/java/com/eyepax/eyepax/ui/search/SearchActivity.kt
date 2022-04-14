package com.eyepax.eyepax.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyepax.eyepax.R
import com.eyepax.eyepax.data.entities.Article
import com.eyepax.eyepax.data.entities.NewsBase
import com.eyepax.eyepax.data.remote.RetrofitClient
import com.eyepax.eyepax.data.repository.NewsRepository
import com.eyepax.eyepax.databinding.ActivitySearchBinding
import com.eyepax.eyepax.ui.main.NewsViewModel
import com.eyepax.eyepax.ui.main.NewsViewModelProviderFactory
import com.eyepax.eyepax.ui.newsdetails.NewsDetailsActivity
import com.eyepax.eyepax.utils.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import kotlin.concurrent.schedule

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: CategoryRecyclerAdapter
    lateinit var searchText: String
    var country: String? = null
    var category: String? = null
    var language: String = "en"
    var isSearch: Boolean = false
    var page: Int = 1
    var isLoading = false
    val list = mutableListOf<Article>()
    lateinit var searchAdapter: SearchResultsRecyclerAdapter
    private val TAG = "SearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initValue()
        addCategoryDataToRecyclerview()
        observers()
        scrollListener()
        buttonAction()
    }

    private fun buttonAction() {
        binding.cardviewFilter.setOnClickListener {
            bottomSheetConfig()
        }
    }


    private fun bottomSheetConfig() {

        val bottomSheet = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(bottomSheet)

        val languages = bottomSheet.findViewById<RecyclerView>(R.id.recyclerviewLanguage)
        var linearLayoutManager =
            LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
        languages.layoutManager = linearLayoutManager
        var adapter = CategoryRecyclerAdapter(this, LANGUAGE_LIST)
        languages.adapter = adapter
        adapter.onItemClick = { s: String, i: Int ->
            adapter.onClickDesignChange(i)
            language = s

        }

        val countries = bottomSheet.findViewById<RecyclerView>(R.id.recyclerviewCountry)
        var linearLayoutManage =
            LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
        countries.layoutManager = linearLayoutManage
        var adapte = CategoryRecyclerAdapter(this, COUNTRY_LIST)
        countries.adapter = adapte
        adapte.onItemClick = { s: String, i: Int ->
            adapte.onClickDesignChange(i)
            country = s

        }

        bottomSheet.findViewById<Button>(R.id.btnReset).setOnClickListener {
            language = "en"
            country = null
            dialog.dismiss()
        }

        bottomSheet.findViewById<Button>(R.id.btnSave).setOnClickListener {

            viewModel.loadBreakingNewsWithFilter(country, language, category, page)
            dialog.dismiss()
        }


        dialog.show()

    }

    private fun observers() {
        viewModel.newsSearch.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        addSearchDataToRecyclerview(it)
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
        viewModel.breakingNewsWithFilter.observe(this) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let {
                        addSearchDataToRecyclerview(it)
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

    private fun showLoading() {
        binding.lottieAnimation.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideLoading() {
        binding.lottieAnimation.visibility = View.GONE
        isLoading = false
    }

    private fun searchNews() {
        binding.txtSearch.setText(searchText)
        viewModel.loadNewsSearch(searchText, page)
    }

    private fun initValue() {

        val repository = NewsRepository(RetrofitClient.getClient())
        val newsViewModelProviderFactory = NewsViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, newsViewModelProviderFactory)[NewsViewModel::class.java]

        isSearch = intent.getBooleanExtra(BUNDLE_IS_SEARCH, false)
        if (isSearch) {
            binding.linearSeatch.visibility = View.VISIBLE
            searchText = intent.getStringExtra(BUNDLE_SEARCH_TEXT).toString()
            searchNews()
        } else {
            binding.linearSeatch.visibility = View.GONE
            loadBreackingNews()
        }

    }

    private fun loadBreackingNews() {
        viewModel.loadBreakingNewsWithFilter(country, language, category, page)
    }

    private fun addSearchDataToRecyclerview(data: NewsBase) {

        list.addAll(data.articles)

        if (list.size <= 20) {
            var linearLayoutManager =
                LinearLayoutManager(this@SearchActivity)
            binding.recyclerviewSearch.layoutManager = linearLayoutManager
            searchAdapter =
                SearchResultsRecyclerAdapter(list, this)
            binding.recyclerviewSearch.adapter = searchAdapter
            searchAdapter.onItemClick = {
                goToNewsDetailsPage(it)
            }
        } else {
            searchAdapter.addAll(list)
        }
    }

    private fun goToNewsDetailsPage(article: Article) {
        val intent = Intent(this@SearchActivity, NewsDetailsActivity::class.java)
        intent.putExtra(BUNDLE_TITLE, article.title)
        intent.putExtra(BUNDLE_DATE, MainUtility.geStringDateFormatted(article.publishedAt))
        intent.putExtra(BUNDLE_AUTHOR, article.author)
        intent.putExtra(BUNDLE_DESCRIPTION, article.description)
        intent.putExtra(BUNDLE_IMAGE, article.urlToImage)
        startActivity(intent)
    }

    private fun addCategoryDataToRecyclerview() {

        linearLayoutManager =
            LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerviewCategory.layoutManager = linearLayoutManager
        adapter = CategoryRecyclerAdapter(this, CATEGORY_LIST)
        binding.recyclerviewCategory.adapter = adapter
        adapter.onItemClick = { s: String, i: Int ->
            adapter.onClickDesignChange(i)
            category = s
            viewModel.loadBreakingNewsWithFilter(country, language, category, page)
        }
    }

    private fun scrollListener() {
        binding.recyclerviewSearch.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.recyclerviewSearch.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!isLoading) {
                        showLoading()
                        Timer("search", false).schedule(PAGINATION_POST_DELAY) {
                            page++
                            if (isSearch) {
                                viewModel.loadNewsSearch(searchText, page)
                            } else {
                                viewModel.loadBreakingNewsWithFilter(
                                    country,
                                    language,
                                    category,
                                    page
                                )
                            }
                        }
                    }
                }
            }
        })
    }
}