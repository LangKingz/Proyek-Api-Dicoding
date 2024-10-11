package com.example.store_api.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(event: FavoriteEvent)

    @Delete
    suspend fun removeFavorite(event: FavoriteEvent)

    @Query("SELECT * FROM favorite_event")
    suspend fun getAll(): List<FavoriteEvent>

    @Query("SELECT * FROM favorite_event WHERE id = :eventId LIMIT 1")
    suspend fun getFavorite(eventId: String): FavoriteEvent?
}