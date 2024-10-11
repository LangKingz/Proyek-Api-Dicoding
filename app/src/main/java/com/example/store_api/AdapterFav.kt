package com.example.store_api

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.store_api.Detail.detailEvent.detailEventActivity
import com.example.store_api.data.room.FavoriteEvent
import com.example.store_api.databinding.ItemFavoriteBinding

class AdapterFav(private val ListFav: List<FavoriteEvent>) :RecyclerView.Adapter<AdapterFav.ViewHolder>() {
    class ViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  ListFav.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorit = ListFav[position]
        holder.binding.JudulFav.text = favorit.name
        holder.binding.ownerFav.text = favorit.category
        Glide.with(holder.itemView.context)
            .load(favorit.imageUrl)
            .into(holder.binding.FavImage)
        holder.itemView.setOnClickListener{
            val seeDetails = Intent(it.context,detailEventActivity::class.java)
            seeDetails.putExtra(detailEventActivity.EXTRA_ID,favorit.id.toInt())
            Log.d("AdapterFav", "ID : ${favorit.id}")
            it.context.startActivity(seeDetails)
        }

    }
}