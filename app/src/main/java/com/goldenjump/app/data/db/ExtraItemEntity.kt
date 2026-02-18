package com.goldenjump.app.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "extra_items",
    indices = [Index(value = ["key"], unique = true)]
)
data class ExtraItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val key: String,
    val name: String,
    val unitPrice: Double,
    val isIncluded: Boolean,
    val updatedAt: Long
)