package org.utl.veterinaria_garrita.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.model.Mascota

@Dao
interface MascotaDao {


    @Query("SELECT * FROM mascota WHERE clienteId = :idCliente ")
    fun getMascotaByCliente(idCliente: Long): Flow<List<Mascota>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMascota(mascota: Mascota): Long

    @Delete
    suspend fun eliminarMascota(mascota: Mascota)

    @Update
    suspend fun actualizarMascota(mascota: Mascota)
}