package com.goldenjump.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.goldenjump.app.data.local.entity.ExtraItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExtraItemDao {
    @Query("SELECT * FROM extra_items ORDER BY isIncluded DESC, name ASC")
    fun observeAll(): Flow<List<ExtraItemEntity>>

    @Query("SELECT * FROM extra_items WHERE isIncluded = 1 ORDER BY name ASC")
    fun observeIncluded(): Flow<List<ExtraItemEntity>>

    @Query("SELECT COUNT(*) FROM extra_items")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(item: ExtraItemEntity): Long

    @Update
    suspend fun update(item: ExtraItemEntity)

    @Query("UPDATE extra_items SET unitPrice = :price, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updatePrice(id: Long, price: Double, updatedAt: Long)

    @Query("UPDATE extra_items SET isIncluded = :included, updatedAt = :updatedAt WHERE id = :id")
    suspend fun setIncluded(id: Long, included: Boolean, updatedAt: Long)
}