package org.utl.veterinaria_garrita.db.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.veterinaria_garrita.db.model.Mascota
import org.utl.veterinaria_garrita.db.repositorio.MascotaRepositorio

class MascotaViewModel (val repositorio: MascotaRepositorio): ViewModel(){
     private val _uiState = mutableStateOf(MascotaUiState())
    val uiState: State<MascotaUiState> = _uiState

    private val _clienteId = mutableStateOf<Long?>(null)
    val clienteId: Long? get() = _clienteId.value


    fun getMascotasByCliente(idCliente: Long): StateFlow<List<Mascota>>{
        _clienteId.value = idCliente
        return repositorio.getMascotaByCliente(idCliente).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun agregarMascota(
        clienteId: Long,
        nombreMascota: String,
        raza: String,
        edad: Int,
        peso: Double
    ) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            isloading = true,
            mensaje = null,
            error = null
        )
        try {
            val mascota = Mascota(
                clienteId = clienteId,
                nombreMascota = nombreMascota,
                raza = raza,
                edad = edad,
                peso = peso
            )
            repositorio.insertarMascota(mascota)

            _uiState.value = _uiState.value.copy(
                isloading = false,
                mensaje = "mascota agregado correctamente",
                error = null
            )
        }
        catch (e: Exception){
            _uiState.value = _uiState.value.copy(
                isloading = false,
                mensaje = null,
                error = "no se pudo agregar a la mascota ${e.message}"
            )
        }
        fun eliminarMascota(mascota: Mascota) = viewModelScope.launch {
            repositorio.eliminarMascota(mascota)
        }

        fun actualizarMascota(mascota: Mascota) = viewModelScope.launch {
            repositorio.actualizarMascota(mascota)
        }

        fun limpiarMensaje(){
            _uiState.value = _uiState.value.copy(
                mensaje = null,
                error = null
            )
        }
    }
}



data class MascotaUiState(
    val isloading: Boolean= false,
    val mensaje: String? = null,
    val error: String?= null
)