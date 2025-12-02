package org.utl.veterinaria_garrita.db.repositorio

import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.dao.InventarioDao
import org.utl.veterinaria_garrita.db.model.Inventario

class InventarioRepositorio(private val inventarioDao: InventarioDao) {
    //todas los items del inventario
    val allInventario: Flow<List<Inventario>> = inventarioDao.getAllInventario()

    //mostrar items por categoria
    fun getInventarioByCategoria(categoria: String): Flow<List<Inventario>> {
       return inventarioDao.getInventarioByCategoria(categoria)
    }

    //obtener item por id
    suspend fun getInventarioById(idInventario: Long): Inventario? {
        return inventarioDao.getInventarioById(idInventario)
    }

    //insertar item
    suspend fun insertarInventario(inventario: Inventario): Long{
        return inventarioDao.insertInventario(inventario)
    }

    //eliminar item
    suspend fun eliminarInventario(inventario: Inventario){
        inventarioDao.eliminarInventario(inventario)
    }

    //actualizar item
    suspend fun actualizarInventario(inventario: Inventario){
        inventarioDao.actualizarInventario(inventario)
    }
}