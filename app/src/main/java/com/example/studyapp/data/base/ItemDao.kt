package com.example.studyapp.data.base

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.studyapp.data.base.CardDataItem

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CardDataItem)

    @Update
    suspend fun update(item: CardDataItem)

    @Delete
    suspend fun delete(item: CardDataItem)

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItem(id: Int): List<CardDataItem>

    @Query("SELECT * FROM items")
    fun getItems(): LiveData<List<CardDataItem>>
}
