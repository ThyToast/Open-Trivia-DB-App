package com.example.opentriviadbdemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriviadbdemoapp.R
import com.example.opentriviadbdemoapp.data.model.QuizCategoryCount
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.databinding.ItemBrowseCardviewBinding
import com.example.opentriviadbdemoapp.databinding.ItemCatalogCardviewBinding



class BaseRecyclerAdapter : RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder<*>>() {
    private var questionList = emptyList<QuizQuestion>()
    private var categoryList = emptyList<QuizCategoryCount>()


    companion object {
        private const val TYPE_BROWSE = 0
        private const val TYPE_CATALOG = 1
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    inner class BrowseViewHolder(private val binding: ItemBrowseCardviewBinding, itemView: View) :
        BaseViewHolder<ItemBrowseCardviewBinding>(itemView) {

        override fun bind(item: ItemBrowseCardviewBinding) {
            val convertText =
                HtmlCompat.fromHtml(
                    questionList[adapterPosition].quizQuestion,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            item.tvQuestion.text = convertText.toString()
            item.chDifficulty.text = questionList[adapterPosition].quizDifficulty
            item.chType.text = questionList[adapterPosition].quizType
        }
    }

    inner class CatalogViewHolder(private val binding: ItemCatalogCardviewBinding, itemView: View) :
        BaseViewHolder<ItemCatalogCardviewBinding>(itemView) {

        override fun bind(item: ItemCatalogCardviewBinding) {
            val category =
                categoryList[adapterPosition].easyQuestion.toString() +
                        "\n\n" + categoryList[adapterPosition].mediumQuestion.toString() +
                        "\n\n" + categoryList[adapterPosition].hardQuestion.toString()

            item.tvCatalogCategory.text = category
            item.tvCatalogTotal.text = categoryList[adapterPosition].totalQuestion.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_BROWSE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_browse_cardview,
                    parent,
                    false
                )
                BaseViewHolder(view)
            }
            TYPE_CATALOG ->{
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_catalog_cardview,
                    parent,
                    false
                )
                BaseViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder){
            is BrowseViewHolder ->{
                holder.bind(questionList as ItemBrowseCardviewBinding )
            }
            is CatalogViewHolder ->{
                holder.bind(categoryList as ItemCatalogCardviewBinding)
            }
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}




