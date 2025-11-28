package org.utl.veterinaria_garrita.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "mascota",
    foreignKeys = [ForeignKey(
        entity = Cliente::class,
        parentColumns = ["idCliente"],
        childColumns = ["clienteid"],
        onDelete = ForeignKey.Companion.CASCADE
    )], indices = [Index("clienteId")])
data class Mascota(
    @PrimaryKey(autoGenerate = true)
    val idMascota: Long = 0,
    val nombreMascota: String,
    val raza: String,
    val edad: Int,
    val peso: Double,
    val clienteId: Long
)