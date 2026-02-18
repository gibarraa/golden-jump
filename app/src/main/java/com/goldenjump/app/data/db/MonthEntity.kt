package com.goldenjump.app.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "months",
    indices = [
        Index(value = ["year", "month"], unique = true)
    ]
)
data class MonthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "month")
    val month: Int,

    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis()
)