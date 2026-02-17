package com.goldenjump.app.data.repo

import com.goldenjump.app.data.db.EmployeeDao
import com.goldenjump.app.data.db.EmployeeEntity
import com.goldenjump.app.data.db.ExtraItemDao
import com.goldenjump.app.data.db.ExtraItemEntity
import kotlinx.coroutines.flow.Flow

class AdminRepository(
    private val employeeDao: EmployeeDao,
    private val extraItemDao: ExtraItemDao
) {
    fun observeEmployees(): Flow<List<EmployeeEntity>> = employeeDao.observeAll()
    fun observeExtraItems(): Flow<List<ExtraItemEntity>> = extraItemDao.observeAll()

    suspend fun seedIfNeeded() {
        if (employeeDao.count() == 0) {
            employeeDao.insert(EmployeeEntity(name = "Empleado Ejemplo 1", employeeNumber = "100001", position = "Crew"))
            employeeDao.insert(EmployeeEntity(name = "Empleado Ejemplo 2", employeeNumber = "100002", position = "Entrenador"))
            employeeDao.insert(EmployeeEntity(name = "Empleado Ejemplo 3", employeeNumber = "100003", position = "Gerente"))
        }

        if (extraItemDao.count() == 0) {
            extraItemDao.insert(ExtraItemEntity(key = "tocino", name = "TOCINOS", unitPrice = 20.94, isIncluded = true))
            extraItemDao.insert(ExtraItemEntity(key = "papas_grandes_mctrio", name = "PAPAS GRANDES (MCTRIOS)", unitPrice = 8.62, isIncluded = true))
            extraItemDao.insert(ExtraItemEntity(key = "coberturas_extras", name = "COBERTURAS EXTRAS", unitPrice = 13.62, isIncluded = true))
            extraItemDao.insert(ExtraItemEntity(key = "extra_salchicha", name = "EXTRA SALCHICHA", unitPrice = 16.73, isIncluded = true))
            extraItemDao.insert(ExtraItemEntity(key = "extra_queso", name = "EXTRA QUESO", unitPrice = 11.75, isIncluded = true))
        }
    }

    suspend fun upsertEmployee(id: Long?, name: String, number: String, position: String, isActive: Boolean) {
        if (id == null) {
            employeeDao.insert(EmployeeEntity(name = name, employeeNumber = number, position = position, isActive = isActive))
        } else {
            employeeDao.update(EmployeeEntity(id = id, name = name, employeeNumber = number, position = position, isActive = isActive))
        }
    }

    suspend fun setEmployeeActive(id: Long, active: Boolean) {
        employeeDao.setActive(id, active)
    }

    suspend fun updateExtraPrice(id: Long, price: Double) {
        extraItemDao.updatePrice(id, price)
    }
}