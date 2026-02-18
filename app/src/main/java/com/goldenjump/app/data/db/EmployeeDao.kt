package com.goldenjump.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employees ORDER BY id DESC")
    fun observeAll(): Flow<List<EmployeeEntity>>

    @Query("SELECT COUNT(*) FROM employees")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: EmployeeEntity): Long

    @Query("UPDATE employees SET isActive = :active WHERE id = :id")
    suspend fun setActive(id: Long, active: Boolean)
}