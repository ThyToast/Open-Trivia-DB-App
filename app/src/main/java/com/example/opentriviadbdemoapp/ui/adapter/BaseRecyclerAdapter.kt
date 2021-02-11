package com.example.opentriviadbdemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.data.model.QuizCategoryCount
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.databinding.ItemBrowseCardviewBinding
import com.example.opentriviadbdemoapp.databinding.ItemCatalogCardviewBinding


class BaseRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = emptyList<Any>()

    companion object {
        private const val TYPE_BROWSE = 0
        private const val TYPE_CATALOG = 1
    }


    class BrowseViewHolder(binding: ItemBrowseCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvQuestion = binding.tvQuestion
        val chDifficulty = binding.chDifficulty
        val chType = binding.chType
    }

    inner class CatalogViewHolder(binding: ItemCatalogCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvCategoryEasy = binding.tvCatalogCategoryEasy
        val tvCategoryMedium = binding.tvCatalogCategoryMedium
        val tvCategoryHard = binding.tvCatalogCategoryHard
        val tvTotal = binding.tvCatalogTotal

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

        } else if (holder is CatalogViewHolder && item is QuizCategoryCount) {
            val resources = holder.itemView.context.resources

            holder.tvCategoryEasy.text =
                resources.getString(R.string.easy, item.easyQuestion)

            holder.tvCategoryMedium.text =
                resources.getString(R.string.medium, item.mediumQuestion)

            holder.tvCategoryHard.text =
                resources.getString(R.string.hard, item.hardQuestion)

            holder.tvTotal.text = item.totalQuestion.toString()

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[0] is QuizQuestion) {
            TYPE_BROWSE
        } else {
            TYPE_CATALOG
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




