package org.utl.veterinaria_garrita.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.model.Inventario

@Dao
interface InventarioDao {

    @Query("SELECT * FROM inventario")
    fun getAllInventario(): Flow<List<Inventario>>

    @Query("SELECT * FROM inventario WHERE categoria = :categoria")
    fun getInventarioByCategoria(categoria: String): Flow<List<Inventario>>

    @Query("SELECT * FROM inventario WHERE idInventario = :idInventario")
    suspend fun getInventarioById(idInventario: Long): Inventario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventario(inventario: Inventario): Long

    @Delete
    suspend fun eliminarInventario(inventario: Inventario)

    @Update
    suspend fun actualizarInventario(inventario: Inventario)
}