package com.example.store_api

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.store_api.Detail.detailEvent.detailEventActivity

class AdapterList(private val listReview: ArrayList<String>) : RecyclerView.Adapter<AdapterList.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        return ViewHolder(view)
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

            holder.title.text = title
            holder.tag.text = tag
            Glide.with(holder.itemView.context)
                .load(logo)
                .into(holder.logo)
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .into(holder.image)

            holder.itemView.setOnClickListener {
                val moveWithId = Intent(it.context, detailEventActivity::class.java)
                moveWithId.putExtra(detailEventActivity.EXTRA_ID, id.toInt())
                it.context.startActivity(moveWithId)
            }
        }else{
            Log.d("AdapterList", "Data tidak lengkap")
        }

    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val title : TextView = view.findViewById(R.id.title)
        val logo : ImageView = view.findViewById(R.id.logo)
        val tag: TextView = view.findViewById(R.id.kategori)
        val image: ImageView = view.findViewById(R.id.ItemImage)

    }
}