package com.example.opentriviadbdemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriviadbdemoapp.data.model.QuizQuestion
import com.example.opentriviadbdemoapp.databinding.ItemBrowseCardviewBinding

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var list = emptyList<QuizQuestion>()

    inner class ViewHolder(val binding: ItemBrowseCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBrowseCardviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val convertText =
            HtmlCompat.fromHtml(list[position].quizQuestion, HtmlCompat.FROM_HTML_MODE_LEGACY)

        holder.binding.tvQuestion.text = convertText.toString()
        holder.binding.chDifficulty.text = list[position].quizDifficulty
        holder.binding.chType.text = list[position].quizType

//        holder.itemView.setOnClickListener { v ->
//            v.context as AppCompatActivity
//        }
    }

    fun setData(newList: List<QuizQuestion>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

