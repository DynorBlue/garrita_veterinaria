package org.utl.veterinaria_garrita.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "cliente", foreignKeys = [ForeignKey(
    entity = Usuario::class,
    parentColumns = ["idUsuario"],
    childColumns = ["usuarioId"],
    onDelete = ForeignKey.Companion.CASCADE
)],
    indices = [Index("usuarioId")])
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val idCliente: Long = 0,
    val nombreCompleto: String,
    val usuarioId: Long
)