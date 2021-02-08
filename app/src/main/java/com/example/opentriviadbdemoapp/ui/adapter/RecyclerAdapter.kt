package com.example.opentriviadbdemoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.opentriviadbdemoapp.databinding.ItemCatalogCardviewBinding

class RecyclerAdapter() : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCatalogCardviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCatalogCardviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.tvTitle.text = list[position].movieTitle
//        holder.binding.tvYear.text = list[position].movieYear
//        Glide.with(holder.itemView.context).load(list[position].moviePoster)
//            .placeholder(R.mipmap.ic_launcher_round).into(holder.binding.ivImage)
//
//        holder.itemView.setOnClickListener { v ->
//            v.context as AppCompatActivity
//        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    //    fun setData(newList: ) {
//        list = newList.Search as ArrayList<>
//        notifyDataSetChanged()
//    }
//

}

