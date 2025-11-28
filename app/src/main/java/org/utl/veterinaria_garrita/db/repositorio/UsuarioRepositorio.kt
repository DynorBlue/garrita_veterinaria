package org.utl.veterinaria_garrita.db.repositorio

import kotlinx.coroutines.flow.Flow
import org.utl.veterinaria_garrita.db.dao.UsuarioDao
import org.utl.veterinaria_garrita.db.model.Usuario

class UsuarioRepositorio (private val usuarioDao: UsuarioDao){

    val allUsuarios: Flow<List<Usuario>> = usuarioDao.getAllUsuarios()

    suspend fun login(usuarioNombre: String, contrasenaUsuario: String): Usuario{
     return   usuarioDao.login(usuarioNombre, contrasenaUsuario)
    }

    suspend fun agregarUsuario(usuario: Usuario): Long{
        return usuarioDao.insertUsuario(usuario)
    }

    suspend fun actualizarUsuario(usuario: Usuario){
        usuarioDao.actualizarUsuario(usuario)
    }

    suspend fun eliminarUsuario(usuario: Usuario){
        usuarioDao.eliminarUsuario(usuario)
    }
}