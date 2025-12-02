package org.utl.veterinaria_garrita.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.model.Cita
@Dao
interface CitaDao {

    @Query("SELECT * FROM cita")
    fun getAllCitas(): Flow<List<Cita>>

    @Query("SELECT * FROM cita WHERE usuarioId = :idUsuario")
    fun getCitasByUsuario(idUsuario: Long): Flow<List<Cita>>

    @Query("SELECT * FROM cita WHERE mascotaId = :idMascota")
    fun getCitaByMascota(idMascota: Long): Flow<List<Cita>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCita(cita: Cita): Long

    @Delete
    suspend fun eliminarCita(cita: Cita)

    @Update
    suspend fun actualizarCita(cita: Cita)
}