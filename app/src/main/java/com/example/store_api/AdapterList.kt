package com.example.store_api

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.store_api.Detail.detailEvent.detailEventActivity
import com.example.store_api.databinding.ItemListBinding // Import your generated binding class

class AdapterList(private val listReview: ArrayList<String>) : RecyclerView.Adapter<AdapterList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val splitData = listReview[position].split(";")

        if (splitData.size >= 5) {
            val title = splitData[0]
            val tag = splitData[1]
            val logo = splitData[2]
            val imageUrl = splitData[3]
            val id = splitData[4]

            holder.binding.title.text = title
            holder.binding.kategori.text = tag
            Glide.with(holder.itemView.context)
                .load(logo)
                .into(holder.binding.logo)
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.binding.ItemImage)

            holder.itemView.setOnClickListener {
                val moveWithId = Intent(it.context, detailEventActivity::class.java)
                moveWithId.putExtra(detailEventActivity.EXTRA_ID, id.toInt())
                it.context.startActivity(moveWithId)
            }
        } else {
            Log.d("AdapterList", "Data tidak lengkap")
        }
    }

    class ViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) // Use binding as a parameter
}
