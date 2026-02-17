package com.goldenjump.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.goldenjump.app.ui.AppRoot
import com.goldenjump.app.ui.theme.GoldenJumpTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoldenJumpTheme {
                val navController = rememberNavController()
                AppRoot(navController)
            }
        }
    }
}