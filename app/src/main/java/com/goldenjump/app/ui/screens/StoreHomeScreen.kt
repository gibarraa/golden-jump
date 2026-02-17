package com.goldenjump.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.goldenjump.app.ui.theme.GlassCard

@Composable
fun StoreHomeScreen(onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlassCard(modifier = Modifier) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Panel Restaurante", style = MaterialTheme.typography.headlineSmall)
                Text(
                    "Aquí irá el formulario diario completo (antes/después), venta total, toma/corte con calendario+reloj, cámara obligatoria y top 3 del mes.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Button(onClick = onLogout) { Text("Cerrar sesión") }
    }
}