package com.goldenjump.app.ui.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goldenjump.app.data.db.EmployeeEntity
import com.goldenjump.app.data.db.ExtraItemEntity
import com.goldenjump.app.data.repository.AdminRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AdminViewModel(
    private val repository: AdminRepository
) : ViewModel() {

    val employees: StateFlow<List<EmployeeEntity>> =
        repository.observeEmployees()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    val extraItems: StateFlow<List<ExtraItemEntity>> =
        repository.observeExtraItems()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun saveEmployee(
        id: Long?,
        name: String,
        number: String,
        position: String,
        active: Boolean
    ) {
        viewModelScope.launch {
            repository.upsertEmployee(
                id = id,
                name = name,
                number = number,
                position = position,
                isActive = active
            )
        }
    }

    fun toggleEmployee(id: Long, active: Boolean) {
        viewModelScope.launch {
            repository.setEmployeeActive(id, active)
        }
    }

    fun updateExtraPrice(id: Long, price: Double) {
        viewModelScope.launch {
            repository.updateExtraPrice(id, price)
        }
    }

    fun setExtraIncluded(id: Long, included: Boolean) {
        viewModelScope.launch {
            repository.setExtraIncluded(id, included)
        }
    }

    fun seedIfNeeded() {
        viewModelScope.launch {
            repository.seedIfNeeded()
        }
    }
}