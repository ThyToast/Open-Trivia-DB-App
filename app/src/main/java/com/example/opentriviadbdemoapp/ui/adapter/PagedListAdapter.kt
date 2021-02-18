package com.example.opentriviadbdemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.databinding.ItemBrowseCardviewBinding

class PagedListAdapter : PagedListAdapter<QuizQuestion, RecyclerView.ViewHolder>(NewsDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BrowseViewHolder(
            ItemBrowseCardviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BrowseViewHolder) {
            val item = getItem(position)
            if (item != null) {
                val convertText =
                    HtmlCompat.fromHtml(item.quizQuestion, HtmlCompat.FROM_HTML_MODE_LEGACY)
                holder.tvQuestion.text = convertText
                holder.chDifficulty.text = item.quizDifficulty
                holder.chType.text = item.quizType
            }
        }
    }

    class BrowseViewHolder(binding: ItemBrowseCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvQuestion = binding.tvQuestion
        val chDifficulty = binding.chDifficulty
        val chType = binding.chType
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<QuizQuestion>() {
            override fun areItemsTheSame(oldItem: QuizQuestion, newItem: QuizQuestion): Boolean {
                return oldItem.quizQuestion == newItem.quizQuestion
            }

            override fun areContentsTheSame(oldItem: QuizQuestion, newItem: QuizQuestion): Boolean {
                return oldItem == newItem
            }
        }
    }
}