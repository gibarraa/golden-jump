package com.goldenjump.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goldenjump.app.data.db.EmployeeEntity
import com.goldenjump.app.data.db.ExtraItemEntity
import com.goldenjump.app.ui.admin.AdminViewModel

@Composable
fun AdminHomeScreen(onLogout: () -> Unit) {
    val vm = remember {
        AdminViewModel(
            com.goldenjump.app.AppGraph.adminRepository
        )
    }

    var tab by remember { mutableIntStateOf(0) } // 0 empleados, 1 precios

    // ✅ NUNCA uses StateFlow.value en composables. Siempre collectAsState.
    val employees by vm.employees.collectAsState()
    val extras by vm.extraItems.collectAsState()

    var showEmployeeDialog by remember { mutableStateOf(false) }
    var editingEmployee by remember { mutableStateOf<EmployeeEntity?>(null) }

    var showPriceDialog by remember { mutableStateOf(false) }
    var editingExtra by remember { mutableStateOf<ExtraItemEntity?>(null) }
    var priceText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { tab = 0 }, modifier = Modifier.weight(1f)) { Text("Empleados") }
            Button(onClick = { tab = 1 }, modifier = Modifier.weight(1f)) { Text("Precios") }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Panel Admin")
            TextButton(onClick = onLogout) { Text("Cerrar sesión") }
        }

        HorizontalDivider()

        if (tab == 0) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Empleados")
                Button(onClick = {
                    editingEmployee = null
                    showEmployeeDialog = true
                }) { Text("Agregar") }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(employees) { e ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("${e.name}  •  #${e.employeeNumber}")
                            Text("Puesto: ${e.position}")

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(if (e.isActive) "Activo" else "Inactivo")
                                Switch(
                                    checked = e.isActive,
                                    onCheckedChange = { vm.toggleEmployee(e.id, it) }
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TextButton(onClick = {
                                    editingEmployee = e
                                    showEmployeeDialog = true
                                }) { Text("Editar") }
                            }
                        }
                    }
                }
            }
        } else {
            Text("Extras / Precios")

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(extras) { x ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(x.name)
                            Text("Precio: ${x.unitPrice}")

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(if (x.isIncluded) "Incluido" else "No incluido")
                                Switch(
                                    checked = x.isIncluded,
                                    onCheckedChange = { vm.setExtraIncluded(x.id, it) }
                                )
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                TextButton(onClick = {
                                    editingExtra = x
                                    priceText = x.unitPrice.toString()
                                    showPriceDialog = true
                                }) { Text("Cambiar precio") }
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
        AlertDialog(
            onDismissRequest = { showPriceDialog = false },
            title = { Text("Actualizar precio") },
            text = {
                OutlinedTextField(
                    value = priceText,
                    onValueChange = { priceText = it },
                    label = { Text("Precio") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(onClick = {
                    val extra = editingExtra ?: return@Button
                    val newPrice = priceText.toDoubleOrNull() ?: 0.0
                    vm.updateExtraPrice(extra.id, newPrice)
                    showPriceDialog = false
                }) { Text("Guardar") }
            },
            dismissButton = {
                TextButton(onClick = { showPriceDialog = false }) { Text("Cancelar") }
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
        title = { Text(if (initial == null) "Agregar empleado" else "Editar empleado") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
                OutlinedTextField(value = number, onValueChange = { number = it }, label = { Text("Número") })
                OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("Puesto") })

                Spacer(Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (active) "Activo" else "Inactivo")
                    Switch(checked = active, onCheckedChange = { active = it })
                }
            }
        },
        confirmButton = {
            Button(onClick = { onSave(initial?.id, name, number, position, active) }) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}