package com.goldenjump.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goldenjump.app.data.db.EmployeeEntity
import com.goldenjump.app.data.db.ExtraItemEntity
import com.goldenjump.app.ui.admin.AdminViewModel
import com.goldenjump.app.ui.theme.GlassCard
import com.goldenjump.app.ui.theme.McDYellow

@Composable
fun AdminHomeScreen(onLogout: () -> Unit) {
    val vm: AdminViewModel = viewModel()

    var tabIndex by remember { mutableIntStateOf(0) }

    var showEmployeeDialog by remember { mutableStateOf(false) }
    var editingEmployee by remember { mutableStateOf<EmployeeEntity?>(null) }

    var showPriceDialog by remember { mutableStateOf(false) }
    var editingPrice by remember { mutableStateOf<ExtraItemEntity?>(null) }

    val employees by vm.employees.collectAsState()
    val extraItems by vm.extraItems.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Admin • Golden Jump", style = MaterialTheme.typography.headlineSmall)
                        Text(
                            "Empleados y precios (según Excel).",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                        )
                    }
                    Button(onClick = onLogout) { Text("Cerrar sesión") }
                }

                TabRow(selectedTabIndex = tabIndex) {
                    Tab(selected = tabIndex == 0, onClick = { tabIndex = 0 }, text = { Text("Empleados") })
                    Tab(selected = tabIndex == 1, onClick = { tabIndex = 1 }, text = { Text("Precios") })
                }
            }
        }

        if (tabIndex == 0) {
            GlassCard(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Empleados", style = MaterialTheme.typography.titleLarge)
                        Button(
                            onClick = { editingEmployee = null; showEmployeeDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = McDYellow,
                                contentColor = MaterialTheme.colorScheme.background
                            )
                        ) { Text("Agregar") }
                    }

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(employees, key = { it.id }) { e ->
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(e.name, style = MaterialTheme.typography.titleMedium)
                                        Text(
                                            "#${e.employeeNumber} • ${e.position}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                                        )
                                        Text(
                                            if (e.isActive) "Activo" else "Inactivo",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                                        )
                                    }
                                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                        Button(onClick = { editingEmployee = e; showEmployeeDialog = true }) { Text("Editar") }
                                        Button(onClick = { vm.toggleEmployee(e.id, !e.isActive) }) {
                                            Text(if (e.isActive) "Desactivar" else "Activar")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            GlassCard(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Precios (editable)", style = MaterialTheme.typography.titleLarge)
                    Text(
                        "Conos dobles no entran; aquí solo rubros incluidos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.72f)
                    )

                    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(extraItems, key = { it.id }) { itx ->
                            GlassCard(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(itx.name, style = MaterialTheme.typography.titleMedium)
                                        Text(
                                            "$${"%.2f".format(itx.unitPrice)}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f)
                                        )
                                    }
                                    Button(onClick = { editingPrice = itx; showPriceDialog = true }) { Text("Cambiar precio") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showEmployeeDialog) {
        EmployeeDialog(
            initial = editingEmployee,
            onDismiss = { showEmployeeDialog = false },
            onSave = { id, name, number, position, active ->
                vm.saveEmployee(id, name, number, position, active)
                showEmployeeDialog = false
            }
        )
    }

    if (showPriceDialog) {
        PriceDialog(
            initial = editingPrice,
            onDismiss = { showPriceDialog = false },
            onSave = { id, price ->
                vm.updatePrice(id, price)
                showPriceDialog = false
            }
        )
    }
}

@Composable
private fun EmployeeDialog(
    initial: EmployeeEntity?,
    onDismiss: () -> Unit,
    onSave: (Long?, String, String, String, Boolean) -> Unit
) {
    var name by remember { mutableStateOf(initial?.name ?: "") }
    var number by remember { mutableStateOf(initial?.employeeNumber ?: "") }
    var position by remember { mutableStateOf(initial?.position ?: "") }
    var active by remember { mutableStateOf(initial?.isActive ?: true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = { onSave(initial?.id, name.trim(), number.trim(), position.trim(), active) }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
        title = { Text(if (initial == null) "Agregar empleado" else "Editar empleado") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    label = { Text("Número empleado") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = position,
                    onValueChange = { position = it },
                    label = { Text("Puesto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = { active = true }) { Text("Activo") }
                    Button(onClick = { active = false }) { Text("Inactivo") }
                }
            }
        }
    )
}

@Composable
private fun PriceDialog(
    initial: ExtraItemEntity?,
    onDismiss: () -> Unit,
    onSave: (Long, Double) -> Unit
) {
    var value by remember { mutableStateOf(if (initial == null) "" else "%.2f".format(initial.unitPrice)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                val p = value.replace(",", ".").toDoubleOrNull()
                if (initial != null && p != null && p > 0) onSave(initial.id, p)
            }) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
        title = { Text("Cambiar precio") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(initial?.name ?: "")
                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("Precio unitario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        }
    )
}