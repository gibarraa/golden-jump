package com.goldenjump.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecordDao {

    @Query("SELECT * FROM daily_records ORDER BY workDate DESC, id DESC")
    fun observeAll(): Flow<List<DailyRecordEntity>>

    @Query("SELECT * FROM daily_records WHERE isApproved = 0 ORDER BY workDate DESC, id DESC")
    fun observePending(): Flow<List<DailyRecordEntity>>

    @Query("SELECT * FROM daily_records WHERE workDate = :workDate ORDER BY id DESC")
    fun observeByWorkDate(workDate: Long): Flow<List<DailyRecordEntity>>

    @Query("SELECT * FROM daily_records WHERE employeeId = :employeeId ORDER BY workDate DESC, id DESC")
    fun observeByEmployee(employeeId: Long): Flow<List<DailyRecordEntity>>

    @Query("SELECT COUNT(*) FROM daily_records WHERE isApproved = 0")
    suspend fun countPending(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: DailyRecordEntity): Long

    @Query("UPDATE daily_records SET isApproved = :approved WHERE id = :id")
    suspend fun setApproved(id: Long, approved: Boolean)

    @Query("DELETE FROM daily_records")
    suspend fun deleteAll()
}