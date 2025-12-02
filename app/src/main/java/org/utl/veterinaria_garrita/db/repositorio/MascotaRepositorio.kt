package org.utl.veterinaria_garrita.db.repositorio

import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.dao.MascotaDao
import org.utl.veterinaria_garrita.db.model.Mascota
class MascotaRepositorio (private val mascotaDao: MascotaDao){

    val allMascotas: Flow<List<Mascota>> = mascotaDao.getAllMascotas()

    fun getMascotaByCliente(idCliente: Long): Flow<List<Mascota>>{
        return mascotaDao.getMascotaByCliente(idCliente)
    }

    suspend fun insertarMascota(mascota: Mascota): Long{
        return mascotaDao.insertarMascota(mascota)
    }

    suspend fun eliminarMascota(mascota: Mascota){
        mascotaDao.eliminarMascota(mascota)
    }

    suspend fun actualizarMascota(mascota: Mascota){
        mascotaDao.actualizarMascota(mascota)
    }
}