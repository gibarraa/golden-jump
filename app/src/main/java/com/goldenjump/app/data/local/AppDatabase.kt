package com.goldenjump.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.goldenjump.app.data.local.dao.DailyRecordDao
import com.goldenjump.app.data.local.dao.DailyRecordItemDao
import com.goldenjump.app.data.local.dao.EmployeeDao
import com.goldenjump.app.data.local.dao.ExtraItemDao
import com.goldenjump.app.data.local.dao.MonthDao
import com.goldenjump.app.data.local.entity.DailyRecordEntity
import com.goldenjump.app.data.local.entity.DailyRecordItemEntity
import com.goldenjump.app.data.local.entity.EmployeeEntity
import com.goldenjump.app.data.local.entity.ExtraItemEntity
import com.goldenjump.app.data.local.entity.MonthEntity

@Database(
    entities = [
        MonthEntity::class,
        EmployeeEntity::class,
        ExtraItemEntity::class,
        DailyRecordEntity::class,
        DailyRecordItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun monthDao(): MonthDao
    abstract fun employeeDao(): EmployeeDao
    abstract fun extraItemDao(): ExtraItemDao
    abstract fun dailyRecordDao(): DailyRecordDao
    abstract fun dailyRecordItemDao(): DailyRecordItemDao
}