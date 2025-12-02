package org.utl.veterinaria_garrita.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.model.Usuario

@Dao
interface UsuarioDao{

    //inicio de sesion
    @Query("SELECT * FROM usuario WHERE nombreUsuario = :nombreUsuario AND contrasena = :contrasenaUsuario LIMIT 1")
    suspend fun login(nombreUsuario: String, contrasenaUsuario: String): Usuario?

    @Query("SELECT * FROM usuario")
    fun getAllUsuarios(): Flow<List<Usuario>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario): Long

    @Delete
    suspend fun eliminarUsuario(usuario: Usuario)

    @Update
    suspend fun actualizarUsuario(usuario: Usuario)
    
    @Query("SELECT COUNT(*) FROM usuario WHERE nombreUsuario = 'admin'")
    suspend fun adminExists(): Int
}