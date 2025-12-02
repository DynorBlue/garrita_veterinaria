package org.utl.veterinaria_garrita.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.utl.veterinaria_garrita.db.data.ConvertidorRol

@Entity(tableName = "usuario")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val idUsuario: Long = 0,
    val nombreUsuario: String,
    val contrasena: String,
    val edad: Int,
    val genero: Char,
    @TypeConverters(ConvertidorRol::class)
    val rol: Rol
)