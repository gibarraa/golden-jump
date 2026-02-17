package com.goldenjump.app.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "employees",
    indices = [Index(value = ["employeeNumber"], unique = true)]
)
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val employeeNumber: String,
    val position: String,
    val isActive: Boolean = true,
    val createdAt: Long
)