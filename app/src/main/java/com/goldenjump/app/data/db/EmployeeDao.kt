package com.goldenjump.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees ORDER BY isActive DESC, name ASC")
    fun observeAll(): Flow<List<EmployeeEntity>>

    @Query("SELECT COUNT(*) FROM employees")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(employee: EmployeeEntity): Long

    @Update
    suspend fun update(employee: EmployeeEntity)

    @Query("UPDATE employees SET isActive = :active WHERE id = :id")
    suspend fun setActive(id: Long, active: Boolean)
}