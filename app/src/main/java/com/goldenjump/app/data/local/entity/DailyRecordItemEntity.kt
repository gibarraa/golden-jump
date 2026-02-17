package com.goldenjump.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_record_items",
    foreignKeys = [
        ForeignKey(
            entity = DailyRecordEntity::class,
            parentColumns = ["id"],
            childColumns = ["recordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["recordId"]), Index(value = ["extraKey"])]
)
data class DailyRecordItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recordId: Long,
    val extraKey: String,
    val extraNameAtTime: String,
    val unitPriceAtTime: Double,
    val qtyBefore: Int,
    val qtyAfter: Int
)