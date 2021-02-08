package com.example.opentriviadbdemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriviadbdemoapp.databinding.ItemBrowseCardviewBinding

class RecyclerAdapter(
    private var list: ArrayList<String> = ArrayList()

) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

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
        holder.binding.tvQuestion.text = list[position]
        holder.binding.chDifficulty.text = list[position]
        holder.binding.chType.text = list[position]

//        holder.itemView.setOnClickListener { v ->
//            v.context as AppCompatActivity
//        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

