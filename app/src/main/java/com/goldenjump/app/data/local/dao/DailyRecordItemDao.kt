package com.goldenjump.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.goldenjump.app.data.local.entity.DailyRecordItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecordItemDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(items: List<DailyRecordItemEntity>)

    @Query("DELETE FROM daily_record_items WHERE recordId = :recordId")
    suspend fun deleteByRecord(recordId: Long)

    @Query("SELECT * FROM daily_record_items WHERE recordId = :recordId ORDER BY extraNameAtTime ASC")
    fun observeByRecord(recordId: Long): Flow<List<DailyRecordItemEntity>>

    @Query("SELECT * FROM daily_record_items WHERE recordId = :recordId")
    suspend fun getByRecord(recordId: Long): List<DailyRecordItemEntity>
}