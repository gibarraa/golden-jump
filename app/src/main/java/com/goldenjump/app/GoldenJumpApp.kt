package com.goldenjump.app

import android.app.Application

class GoldenJumpApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppGraph.init(this)
    }
}