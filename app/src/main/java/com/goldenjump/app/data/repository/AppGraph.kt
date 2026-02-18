package com.goldenjump.app

import android.content.Context
import com.goldenjump.app.data.db.AppDatabase
import com.goldenjump.app.data.repository.AdminRepository

object AppGraph {

    private lateinit var db: AppDatabase
    lateinit var adminRepository: AdminRepository
        private set

    fun init(context: Context) {
        db = AppDatabase.getInstance(context)
        adminRepository = AdminRepository(db)
    }
}