package com.eyepax.eyepaxtest.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.eyepax.eyepaxtest.R
import com.eyepax.eyepaxtest.databinding.LayoutCategoryItemBinding
import com.eyepax.eyepaxtest.utils.CATEGORY_LIST

class CategoryRecyclerAdapter(val context: Context,val list: List<String>) :
    RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryHolder>() {

    var onItemClick: ((String, Int) -> Unit)? = null
    var selectedPosition: Int = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryRecyclerAdapter.CategoryHolder {

        val itemBinding =
            LayoutCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CategoryHolder(itemBinding)

    }

    fun onClickDesignChange(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CategoryRecyclerAdapter.CategoryHolder, position: Int) {
        val category: String = list[position]
        holder.bind(category)
    }

    override fun getItemCount() = list.size

    inner class CategoryHolder(private val binding: LayoutCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(category: String) {

            binding.textCategory.text = category
            if (adapterPosition == selectedPosition) {
                binding.textCategory.setTextColor(Color.WHITE)
                binding.cardview.setCardBackgroundColor(context.getColor(R.color.primary))
            } else {
                binding.textCategory.setTextColor(Color.BLACK)
                binding.cardview.setCardBackgroundColor(Color.WHITE)
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(list[adapterPosition], adapterPosition)
            }
        }
    }
}

