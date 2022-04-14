package com.eyepax.eyepax.ui.main

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eyepax.eyepax.data.entities.Article
import com.eyepax.eyepax.databinding.LayoutBreakingNewsItemBinding
import com.eyepax.eyepax.utils.MainUtility

class BreakingNewsRecyclerAdapter(private var list: List<Article>, private val context: Context) :
    RecyclerView.Adapter<BreakingNewsRecyclerAdapter.BreakingNewsHolder>() {

    var onItemClick: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BreakingNewsHolder {

        val itemBinding =
            LayoutBreakingNewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return BreakingNewsHolder(itemBinding)

    }

    fun addAll(items: List<Article>) {
        list = items
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(
        holder: BreakingNewsHolder,
        position: Int
    ) {
        val article: Article = list[position]
        holder.bind(article)
    }

    override fun getItemCount() = list.size

    inner class BreakingNewsHolder(val binding: LayoutBreakingNewsItemBinding) :
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

