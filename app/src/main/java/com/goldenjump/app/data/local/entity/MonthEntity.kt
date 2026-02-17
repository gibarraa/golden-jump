package com.goldenjump.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "months",
    indices = [Index(value = ["yearMonth"], unique = true)]
)
data class MonthEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val yearMonth: String,
    val createdAt: Long,
    val isClosed: Boolean = false,
    val closedAt: Long? = null
)