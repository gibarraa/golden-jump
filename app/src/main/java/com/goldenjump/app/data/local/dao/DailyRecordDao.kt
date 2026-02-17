package com.goldenjump.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.goldenjump.app.data.local.entity.DailyRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyRecordDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(record: DailyRecordEntity): Long

    @Update
    suspend fun update(record: DailyRecordEntity)

    @Query("DELETE FROM daily_records WHERE id = :id AND isApproved = 0")
    suspend fun deleteIfNotApproved(id: Long): Int

    @Query("UPDATE daily_records SET isApproved = 1, approvedAt = :approvedAt WHERE id = :id AND isApproved = 0")
    suspend fun approve(id: Long, approvedAt: Long): Int

    @Query("SELECT * FROM daily_records WHERE monthId = :monthId ORDER BY workDate DESC, createdAt DESC")
    fun observeByMonth(monthId: Long): Flow<List<DailyRecordEntity>>

    @Query("SELECT * FROM daily_records WHERE monthId = :monthId AND employeeId = :employeeId ORDER BY workDate DESC, createdAt DESC")
    fun observeByMonthAndEmployee(monthId: Long, employeeId: Long): Flow<List<DailyRecordEntity>>

    @Query("SELECT COUNT(*) FROM daily_records WHERE monthId = :monthId AND isApproved = 1")
    suspend fun countApprovedInMonth(monthId: Long): Int

    @Transaction
    @Query("SELECT * FROM daily_records WHERE id = :recordId LIMIT 1")
    suspend fun getOne(recordId: Long): DailyRecordEntity?
}