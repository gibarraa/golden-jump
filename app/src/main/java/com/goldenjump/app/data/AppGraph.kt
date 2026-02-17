package com.goldenjump.app.data

import android.content.Context
import androidx.room.Room
import com.goldenjump.app.data.db.AppDatabase
import com.goldenjump.app.data.repo.AdminRepository

object AppGraph {
    lateinit var db: AppDatabase
        private set
    lateinit var adminRepo: AdminRepository
        private set

    fun init(context: Context) {
        val appContext = context.applicationContext
        db = Room.databaseBuilder(appContext, AppDatabase::class.java, "golden_jump.db")
            .fallbackToDestructiveMigration()
            .build()

        adminRepo = AdminRepository(db.employeeDao(), db.extraItemDao())
    }
}