package com.eyepax.eyepax.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.eyepax.eyepax.R
import com.eyepax.eyepax.databinding.LayoutCategoryItemBinding
import com.eyepax.eyepax.utils.CATEGORY_LIST

class LanguageRecyclerAdapter(val context: Context) :
    RecyclerView.Adapter<LanguageRecyclerAdapter.LanguageHolder>() {

    var onItemClick: ((String, Int) -> Unit)? = null
    var selectedPosition: Int = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LanguageHolder {

        val itemBinding =
            LayoutCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LanguageHolder(itemBinding)

    }

    fun onClickDesignChange(position: Int) {
        selectedPosition = position
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: LanguageHolder, position: Int) {
        val category: String = CATEGORY_LIST[position]
        holder.bind(category)
    }

    override fun getItemCount() = CATEGORY_LIST.size

    inner class LanguageHolder(private val binding: LayoutCategoryItemBinding) :
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

                onItemClick?.invoke(CATEGORY_LIST[adapterPosition], adapterPosition)

            }
        }
    }
}

