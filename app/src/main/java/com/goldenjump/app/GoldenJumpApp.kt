package com.goldenjump.app

import android.app.Application
import com.goldenjump.app.data.AppGraph

class GoldenJumpApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppGraph.init(this)
    }
}