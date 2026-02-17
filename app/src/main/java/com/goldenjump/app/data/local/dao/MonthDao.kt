package com.goldenjump.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.goldenjump.app.data.local.entity.MonthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthDao {
    @Query("SELECT * FROM months WHERE yearMonth = :yearMonth LIMIT 1")
    suspend fun findByYearMonth(yearMonth: String): MonthEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(month: MonthEntity): Long

    @Update
    suspend fun update(month: MonthEntity)

    @Query("SELECT * FROM months ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<MonthEntity>>
}