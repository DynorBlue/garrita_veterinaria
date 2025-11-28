package org.utl.veterinaria_garrita.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.model.Cliente

@Dao
interface ClienteDao {
    //funcion oara traerme todos los clientes
    @Query("SELECT * FROM cliente")
    fun getAllClientes(): Flow<List<Cliente>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCliente(cliente: Cliente): Long

    @Update
    suspend fun actualizarCliente(cliente: Cliente)

    @Delete
    suspend fun eliminarCliente(cliente: Cliente)

}

