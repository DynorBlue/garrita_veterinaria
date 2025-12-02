package org.utl.veterinaria_garrita.db.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.veterinaria_garrita.db.model.Cliente
import org.utl.veterinaria_garrita.db.repositorio.ClienteRepositorio

class ClienteViewModel (val repositorio: ClienteRepositorio): ViewModel() {
    private val _uiState = mutableStateOf(ClienteUiState())
    val uiState: State<ClienteUiState> = _uiState

    //variable para trabajar con la FK(por si a futuro quiero traer los clientes por quien los agrego)
  private val _usuarioId = mutableStateOf<Long?>(null)
    val usuarioId: Long? get() = _usuarioId.value


    val clientes = repositorio.allClientes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun agregarCliente(
        nombreCompleto: String,
        usuarioId: Long
    ) = viewModelScope.launch {
        try {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                mensaje = null,
                error = null
            )
            val cliente = Cliente(
                nombreCompleto = nombreCompleto,
                usuarioId = usuarioId
            )
            repositorio.insertarCliente(cliente)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                mensaje = "cliente agregado correctamente",
                error = null
            )

        } catch (e: Exception){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                mensaje = null,
                error = "no se pudo agregar el cliente ${e.message}"
            )
        }
    }

    fun actualizarCliente(cliente: Cliente) = viewModelScope.launch {
        repositorio.actualizarCliente(cliente)
    }
    fun eliminarCliente(cliente: Cliente) = viewModelScope.launch {
        repositorio.eliminarCliente(cliente)
    }
    fun limpiarMensaje(){
        _uiState.value = _uiState.value.copy(
            mensaje = null,
            error = null
        )
    }

}


data class ClienteUiState(
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
)