package com.goldenjump.app.data

import android.content.Context
import androidx.room.Room
import com.goldenjump.app.data.db.AppDatabase
import com.goldenjump.app.data.repository.AdminRepository

object AppGraph {

    lateinit var db: AppDatabase
        private set

    lateinit var adminRepo: AdminRepository
        private set

    fun init(context: Context) {
        db = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "goldenjump.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        adminRepo = AdminRepository(db)
    }
}