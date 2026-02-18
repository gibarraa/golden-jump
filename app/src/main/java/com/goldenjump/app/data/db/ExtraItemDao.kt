package com.goldenjump.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtraItemDao {

    @Query("SELECT * FROM extra_items ORDER BY id DESC")
    fun observeAll(): Flow<List<ExtraItemEntity>>

    @Query("SELECT COUNT(*) FROM extra_items")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: ExtraItemEntity): Long

    @Query("UPDATE extra_items SET unitPrice = :price, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updatePrice(id: Long, price: Double, updatedAt: Long)

    @Query("UPDATE extra_items SET isIncluded = :included, updatedAt = :updatedAt WHERE id = :id")
    suspend fun setIncluded(id: Long, included: Boolean, updatedAt: Long)
}