package org.utl.veterinaria_garrita.db.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.veterinaria_garrita.db.model.Rol
import org.utl.veterinaria_garrita.db.model.Usuario
import org.utl.veterinaria_garrita.db.repositorio.UsuarioRepositorio

class UsuarioViewModel(
    private val repositorio: UsuarioRepositorio
) : ViewModel() {
    //estado de ui
    private val _uiState = mutableStateOf(UsuarioUiState())
    val uiState: State<UsuarioUiState> = _uiState

    //mostrar los usuarios

    val usuarios = repositorio.allUsuarios.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
    //funcion para logear usuario

    //agregar usuarios
    fun agregarUsuario(
        nombreUsuario: String,
        contrasena: String,
        edad: Int,
        genero: Char,
        rol: Rol
    ) = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                mensaje = null,
                error = null
            )
            val usuario = Usuario(
                nombreUsuario = nombreUsuario,
                contrasena = contrasena,
                edad = edad,
                genero = genero,
                rol = rol
            )
            repositorio.agregarUsuario(usuario)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                mensaje = "usuario agregado correctamente",
                error = null
            )
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                mensaje = null,
                error = "No se pudo agregar el usuario ${e.message}"
            )
        }
    }


    //funcion actualizar usuario
    fun actualizarUsuario(usuario: Usuario) = viewModelScope.launch {
        repositorio.actualizarUsuario(usuario)
    }

    //funcion para eliminar usuario
    fun eliminarUsuario(usuario: Usuario) = viewModelScope.launch {
        repositorio.eliminarUsuario(usuario)
    }

    //funcion para limpiar mensaje
    fun limpiarMensaje() {
        _uiState.value = _uiState.value.copy(mensaje = null, error = null)
    }
}


data class UsuarioUiState(
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
)