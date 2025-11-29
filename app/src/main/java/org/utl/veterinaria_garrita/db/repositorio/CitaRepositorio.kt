package org.utl.veterinaria_garrita.db.repositorio

import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.dao.CitaDao
import org.utl.veterinaria_garrita.db.model.Cita

class CitaRepositorio(private val citaDao: CitaDao) {
    //todas las citas
    val allCitas: Flow<List<Cita>> = citaDao.getAllCitas()

    //mostrar citas por mascota
    fun getCitasByMascota(idMascota: Long): Flow<List<Cita>> {
       return citaDao.getCitaByMascota(idMascota)
    }

    //mostrar citas por veterinario
    fun getCitasByVeterinario(idUsuario: Long): Flow<List<Cita>>{
        return citaDao.getCitasByUsuario(idUsuario)
    }

    //insertarCita

    suspend fun insertarCita(cita: Cita): Long{
        return citaDao.insertCita(cita)
    }

    //eliminar cita
    suspend fun eliminarCita(cita: Cita){
        citaDao.eliminarCita(cita)
    }

    //actualizar cita
    suspend fun actualizarCita(cita: Cita){
        citaDao.actualizarCita(cita)
    }
}