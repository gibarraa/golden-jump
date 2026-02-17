package com.goldenjump.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        EmployeeEntity::class,
        ExtraItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun extraItemDao(): ExtraItemDao
}