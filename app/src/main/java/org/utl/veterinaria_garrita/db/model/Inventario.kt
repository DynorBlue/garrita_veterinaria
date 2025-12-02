package org.utl.veterinaria_garrita.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventario")
data class Inventario(
    @PrimaryKey(autoGenerate = true)
    val idInventario: Long = 0,
    val nombreProducto: String,
    val descripcion: String,
    val cantidad: Int,
    val precio: Double,
    val categoria: String,
    val fechaRegistro: String
)