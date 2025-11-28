package org.utl.veterinaria_garrita.db.data

import androidx.room.TypeConverter
import org.utl.veterinaria_garrita.db.model.Rol

class ConvertidorRol {
    @TypeConverter
    fun fromRol(rol: Rol): String = rol.name

    @TypeConverter
    fun toRol(valor: String): Rol = Rol.valueOf(valor)
}