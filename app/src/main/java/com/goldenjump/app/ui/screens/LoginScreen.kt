package com.goldenjump.app.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.goldenjump.app.ui.theme.GlassCard
import com.goldenjump.app.ui.theme.McDRed
import com.goldenjump.app.ui.theme.McDYellow

@Composable
fun LoginScreen(
    onAdmin: () -> Unit,
    onStore: () -> Unit
) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.78f)
                .fillMaxHeight(0.68f),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0f)
        ) {
            Row(
                modifier = Modifier.fillMaxSize().padding(18.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                GlassCard(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Golden Jump",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "Saltos Pequeños • Extras",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                        )
                        Spacer(Modifier.height(18.dp))

                        OutlinedTextField(
                            value = user,
                            onValueChange = { user = it.trim(); error = null },
                            label = { Text("Usuario") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(10.dp))
                        OutlinedTextField(
                            value = pass,
                            onValueChange = { pass = it; error = null },
                            label = { Text("Contraseña") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )

                        Spacer(Modifier.height(14.dp))

                        AnimatedVisibility(visible = error != null, enter = fadeIn(), exit = fadeOut()) {
                            Text(
                                text = error ?: "",
                                color = McDRed,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }

                        Spacer(Modifier.height(14.dp))

                        Button(
                            onClick = {
                                when {
                                    user == "139049" && pass == "1234" -> onAdmin()
                                    user == "silao.0857" && pass == "1234" -> onStore()
                                    else -> error = "Usuario o contraseña incorrectos"
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = McDYellow,
                                contentColor = MaterialTheme.colorScheme.background
                            )
                        ) {
                            Text("Entrar")
                        }
                    }
                }

                GlassCard(
                    modifier = Modifier.weight(1f).fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Minimal. Fluido. Validable.",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Captura antes/después, calcula el porcentaje y valida con foto del ticket. Historial por mes y cierre controlado.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Spacer(Modifier.width(2.dp))
                            Text(
                                text = "Admin: 139049 • Store: silao.0857",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                            )
                        }
                    }
                }
            }
        }
    }
}