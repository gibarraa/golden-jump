package com.goldenjump.app.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldenjump.app.data.AppGraph
import com.goldenjump.app.data.db.EmployeeEntity
import com.goldenjump.app.data.db.ExtraItemEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val repo = AppGraph.adminRepo

    val employees: StateFlow<List<EmployeeEntity>> =
        repo.observeEmployees().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val extraItems: StateFlow<List<ExtraItemEntity>> =
        repo.observeExtraItems().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { repo.seedIfNeeded() }
    }

    fun saveEmployee(id: Long?, name: String, number: String, position: String, isActive: Boolean) {
        viewModelScope.launch { repo.upsertEmployee(id, name, number, position, isActive) }
    }

    fun toggleEmployee(id: Long, active: Boolean) {
        viewModelScope.launch { repo.setEmployeeActive(id, active) }
    }

    fun updatePrice(id: Long, price: Double) {
        viewModelScope.launch { repo.updateExtraPrice(id, price) }
    }
}