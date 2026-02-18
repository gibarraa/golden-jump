package com.goldenjump.app.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_records",
    foreignKeys = [
        ForeignKey(
            entity = MonthEntity::class,
            parentColumns = ["id"],
            childColumns = ["monthId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["monthId"]),
        Index(value = ["workDate"]),
        Index(value = ["employeeId"]),
        Index(value = ["isApproved"])
    ]
)
data class DailyRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "monthId")
    val monthId: Long,

    @ColumnInfo(name = "day")
    val day: Int,

    @ColumnInfo(name = "workDate")
    val workDate: Long,

    @ColumnInfo(name = "employeeId")
    val employeeId: Long,

    @ColumnInfo(name = "isApproved")
    val isApproved: Boolean = false,

    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis()
)