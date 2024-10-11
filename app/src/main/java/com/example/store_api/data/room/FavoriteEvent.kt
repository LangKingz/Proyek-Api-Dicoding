package com.example.store_api.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_event")
data class FavoriteEvent (
    @PrimaryKey val id:String,
    val name:String,
    val category: String,
    val logo:String,
    val imageUrl:String,

)