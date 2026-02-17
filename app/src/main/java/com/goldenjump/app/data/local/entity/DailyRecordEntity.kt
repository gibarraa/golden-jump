package com.goldenjump.app.data.local.entity

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
            onDelete = ForeignKey.RESTRICT
        ),
        ForeignKey(
            entity = EmployeeEntity::class,
            parentColumns = ["id"],
            childColumns = ["employeeId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["monthId"]),
        Index(value = ["employeeId"]),
        Index(value = ["monthId", "workDate"])
    ]
)
data class DailyRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val monthId: Long,
    val employeeId: Long,
    val workDate: String,
    val boxTakenAt: Long,
    val boxCutAt: Long,
    val totalSalesBefore: Double,
    val totalSalesAfter: Double,
    val ticketPhotoPath: String,
    val createdAt: Long,
    val isApproved: Boolean = false,
    val approvedAt: Long? = null
)