package com.goldenjump.app.data.repository

import com.goldenjump.app.data.db.AppDatabase
import com.goldenjump.app.data.db.EmployeeEntity
import com.goldenjump.app.data.db.ExtraItemEntity
import kotlinx.coroutines.flow.Flow

class AdminRepository(
    private val db: AppDatabase
) {

    // =========================
    // EMPLOYEES
    // =========================

    fun observeEmployees(): Flow<List<EmployeeEntity>> {
        return db.employeeDao().observeAll()
    }

    suspend fun upsertEmployee(
        id: Long?,
        name: String,
        number: String,
        position: String,
        isActive: Boolean
    ) {
        val now = System.currentTimeMillis()

        // Si es edición, conserva el createdAt original (si existe)
        val createdAt = if (id == null) {
            now
        } else {
            db.employeeDao().getById(id)?.createdAt ?: now
        }

        db.employeeDao().upsert(
            EmployeeEntity(
                id = id ?: 0L,
                name = name,
                employeeNumber = number,
                position = position,
                isActive = isActive,
                createdAt = createdAt
            )
        )
    }

    suspend fun setEmployeeActive(id: Long, active: Boolean) {
        db.employeeDao().setActive(id, active)
    }

    // =========================
    // EXTRA ITEMS
    // =========================

    fun observeExtraItems(): Flow<List<ExtraItemEntity>> {
        return db.extraItemDao().observeAll()
    }

    suspend fun updateExtraPrice(id: Long, price: Double) {
        db.extraItemDao().updatePrice(
            id = id,
            price = price,
            updatedAt = System.currentTimeMillis()
        )
    }

    suspend fun setExtraIncluded(id: Long, included: Boolean) {
        db.extraItemDao().setIncluded(
            id = id,
            included = included,
            updatedAt = System.currentTimeMillis()
        )
    }

    // =========================
    // SEED INICIAL
    // =========================

    suspend fun seedIfNeeded() {
        val now = System.currentTimeMillis()

        if (db.employeeDao().count() == 0) {
            db.employeeDao().upsert(
                EmployeeEntity(
                    name = "Empleado 1",
                    employeeNumber = "001",
                    position = "Crew",
                    isActive = true,
                    createdAt = now
                )
            )
            db.employeeDao().upsert(
                EmployeeEntity(
                    name = "Empleado 2",
                    employeeNumber = "002",
                    position = "Líder",
                    isActive = true,
                    createdAt = now
                )
            )
            db.employeeDao().upsert(
                EmployeeEntity(
                    name = "Empleado 3",
                    employeeNumber = "003",
                    position = "Gerente",
                    isActive = true,
                    createdAt = now
                )
            )
        }

        if (db.extraItemDao().count() == 0) {
            db.extraItemDao().insert(
                ExtraItemEntity(
                    key = "tocino",
                    name = "Tocino",
                    unitPrice = 0.0,
                    isIncluded = true,
                    updatedAt = now
                )
            )

            db.extraItemDao().insert(
                ExtraItemEntity(
                    key = "papas_grandes",
                    name = "Papas Grandes (McTrio)",
                    unitPrice = 0.0,
                    isIncluded = true,
                    updatedAt = now
                )
            )

            db.extraItemDao().insert(
                ExtraItemEntity(
                    key = "coberturas",
                    name = "Coberturas Extras",
                    unitPrice = 0.0,
                    isIncluded = true,
                    updatedAt = now
                )
            )

            db.extraItemDao().insert(
                ExtraItemEntity(
                    key = "salchicha",
                    name = "Salchicha",
                    unitPrice = 0.0,
                    isIncluded = true,
                    updatedAt = now
                )
            )

            db.extraItemDao().insert(
                ExtraItemEntity(
                    key = "queso",
                    name = "Queso",
                    unitPrice = 0.0,
                    isIncluded = true,
                    updatedAt = now
                )
            )

            // CONOS DOBLES NO ENTRAR
            db.extraItemDao().insert(
                ExtraItemEntity(
                    key = "conos_dobles",
                    name = "Conos Dobles",
                    unitPrice = 0.0,
                    isIncluded = false,
                    updatedAt = now
                )
            )
        }
    }
}