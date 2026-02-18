package com.goldenjump.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthDao {

    @Query("SELECT * FROM months ORDER BY year DESC, month DESC")
    fun observeAll(): Flow<List<MonthEntity>>

    @Query("SELECT COUNT(*) FROM months")
    suspend fun count(): Int

    @Query("SELECT * FROM months WHERE year = :year AND month = :month LIMIT 1")
    suspend fun getByYearMonth(year: Int, month: Int): MonthEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entity: MonthEntity): Long

    @Query("UPDATE months SET createdAt = :createdAt WHERE id = :id")
    suspend fun touch(id: Long, createdAt: Long)

    @Query("DELETE FROM months")
    suspend fun deleteAll()

    @Transaction
    suspend fun upsertByYearMonth(year: Int, month: Int): Long {
        val existing = getByYearMonth(year, month)
        return if (existing != null) {
            touch(existing.id, System.currentTimeMillis())
            existing.id
        } else {
            insert(
                MonthEntity(
                    year = year,
                    month = month,
                    createdAt = System.currentTimeMillis()
                )
            )
        }
    }
}