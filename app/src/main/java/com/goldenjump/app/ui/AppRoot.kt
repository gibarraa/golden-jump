package com.goldenjump.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.goldenjump.app.ui.screens.AdminHomeScreen
import com.goldenjump.app.ui.screens.LoginScreen
import com.goldenjump.app.ui.screens.StoreHomeScreen
import com.goldenjump.app.ui.theme.McDBackground

@Composable
fun AppRoot(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        McDBackground(modifier = Modifier.fillMaxSize())
        NavHost(
            navController = navController,
            startDestination = NavRoutes.LOGIN
        ) {
            composable(NavRoutes.LOGIN) {
                LoginScreen(
                    onAdmin = {
                        navController.navigate(NavRoutes.ADMIN) {
                            popUpTo(NavRoutes.LOGIN) { inclusive = true }
                        }
                    },
                    onStore = {
                        navController.navigate(NavRoutes.STORE) {
                            popUpTo(NavRoutes.LOGIN) { inclusive = true }
                        }
                    }
                )
            }
            composable(NavRoutes.ADMIN) {
                AdminHomeScreen(onLogout = {
                    navController.navigate(NavRoutes.LOGIN) { popUpTo(0) }
                })
            }
            composable(NavRoutes.STORE) {
                StoreHomeScreen(onLogout = {
                    navController.navigate(NavRoutes.LOGIN) { popUpTo(0) }
                })
            }
        }
    }
}