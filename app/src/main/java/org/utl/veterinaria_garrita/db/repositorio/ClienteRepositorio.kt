package org.utl.veterinaria_garrita.db.repositorio

import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.dao.ClienteDao
import org.utl.veterinaria_garrita.db.model.Cliente

class ClienteRepositorio (private val clienteDao: ClienteDao){

    val allClientes: Flow<List<Cliente>> = clienteDao.getAllClientes()

    suspend fun insertarCliente(cliente: Cliente): Long{
        return clienteDao.insertCliente(cliente)
    }
    suspend fun actualizarCliente(cliente: Cliente){
        clienteDao.actualizarCliente(cliente)
    }
    suspend fun eliminarCliente(cliente: Cliente){
        clienteDao.eliminarCliente(cliente)
    }
}