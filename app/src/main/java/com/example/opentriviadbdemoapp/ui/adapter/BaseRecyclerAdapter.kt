package com.example.opentriviadbdemoapp.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.data.model.QuizCategoryComposite
import com.example.opentriviadbdemoapp.data.model.QuizCategoryList
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.data.model.QuizResult
import com.example.opentriviadbdemoapp.databinding.ItemBrowseCardviewBinding
import com.example.opentriviadbdemoapp.databinding.ItemCatalogCardviewBinding
import com.example.opentriviadbdemoapp.databinding.ItemQuizResultCardviewBinding


class BaseRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = emptyList<Any>()

    companion object {
        private const val TYPE_BROWSE = 0
        private const val TYPE_CATALOG = 1
        private const val TYPE_RESULT = 2
    }


    inner class BrowseViewHolder(binding: ItemBrowseCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvQuestion = binding.tvQuestion
        val chDifficulty = binding.chDifficulty
        val chType = binding.chType
    }

    inner class CatalogViewHolder(binding: ItemCatalogCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCatalogCategory = binding.tvCatalogCategory
        val tvCategoryEasy = binding.tvCatalogCategoryEasy
        val tvCategoryMedium = binding.tvCatalogCategoryMedium
        val tvCategoryHard = binding.tvCatalogCategoryHard
        val tvTotal = binding.tvCatalogTotal
    }

    inner class ResultViewHolder(binding: ItemQuizResultCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvResultQuestion = binding.tvResultQuestion
        val tvResultAnswer1 = binding.tvResultAnswer1
        val tvResultAnswer2 = binding.tvResultAnswer2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_BROWSE -> {
                return BrowseViewHolder(
                    ItemBrowseCardviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_CATALOG -> {
                return CatalogViewHolder(
                    ItemCatalogCardviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_RESULT -> {
                return ResultViewHolder(
                    ItemQuizResultCardviewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        if (holder is BrowseViewHolder && item is QuizQuestion) {
            val convertText =
                HtmlCompat.fromHtml(item.quizQuestion, HtmlCompat.FROM_HTML_MODE_LEGACY)
            holder.tvQuestion.text = convertText
            holder.chDifficulty.text = item.quizDifficulty
            holder.chType.text = item.quizType

        } else if (holder is CatalogViewHolder && item is QuizCategoryComposite) {
            val resources = holder.itemView.context.resources
            val categoryCount = item.categoryCount

            holder.tvCatalogCategory.text = item.categoryName

            holder.tvCategoryEasy.text =
                resources.getString(R.string.easy, categoryCount.easyQuestion)

            holder.tvCategoryMedium.text =
                resources.getString(R.string.medium, categoryCount.mediumQuestion)

            holder.tvCategoryHard.text =
                resources.getString(R.string.hard, categoryCount.hardQuestion)

            holder.tvTotal.text = categoryCount.totalQuestion.toString()
        } else if (holder is ResultViewHolder && item is QuizResult) {
            val convertText =
                HtmlCompat.fromHtml(
                    item.quizQuestion.quizQuestion,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            holder.tvResultQuestion.text = convertText
            holder.tvResultAnswer1.text = item.quizChosenAnswer

            if (item.quizChosenAnswer == item.quizQuestion.quizCorrectAnswer) {
                holder.tvResultAnswer1.setTextColor(Color.GREEN)
                holder.tvResultAnswer2.isVisible = false
            } else {
                holder.tvResultAnswer1.setTextColor(Color.RED)
                holder.tvResultAnswer2.isVisible = true
                holder.tvResultAnswer2.text = item.quizQuestion.quizCorrectAnswer
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (list[0] is QuizQuestion) {
            TYPE_BROWSE
        } else if (list[0] is QuizCategoryList) {
            TYPE_CATALOG
        } else {
            TYPE_RESULT
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newList: List<Any>) {
        list = newList
        notifyDataSetChanged()
    }

}




