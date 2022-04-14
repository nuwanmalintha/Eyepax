package com.eyepax.eyepax.ui.search

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eyepax.eyepax.data.entities.Article
import com.eyepax.eyepax.databinding.LayoutSearchResultsItemBinding
import com.eyepax.eyepax.utils.MainUtility

class SearchResultsRecyclerAdapter(private var list: List<Article>, private val context: Context) :
    RecyclerView.Adapter<SearchResultsRecyclerAdapter.SearchHolder>() {

    var onItemClick: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchHolder {

        val itemBinding =
            LayoutSearchResultsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return SearchHolder(itemBinding)

    }

    fun addAll(items: List<Article>) {
        list = items
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(
        holder: SearchHolder,
        position: Int
    ) {
        val article: Article = list[position]
        holder.bind(article)
    }

    override fun getItemCount() = list.size

    inner class SearchHolder(val binding: LayoutSearchResultsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(article: Article) {
            binding.textTopic.text = article.title
            binding.textAuthor.text = article.author
            binding.textDate.text = MainUtility.geStringDateFormatted(article.publishedAt)
            Glide.with(context)
                .load(article.urlToImage)
                .centerCrop()
                .into(binding.imgImage)
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(list[adapterPosition])
            }
        }
    }
}

