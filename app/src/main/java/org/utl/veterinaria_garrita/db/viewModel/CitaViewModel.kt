package org.utl.veterinaria_garrita.db.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.veterinaria_garrita.db.model.Cita
import org.utl.veterinaria_garrita.db.model.Mascota
import org.utl.veterinaria_garrita.db.repositorio.CitaRepositorio
import java.util.Date

class CitaViewModel (val repositorio: CitaRepositorio): ViewModel(){
    private val _uiState = mutableStateOf(CitaUiState())
    val uiState: State<CitaUiState> = _uiState

    //variables para trabajar con las FK de usuario y mascota
    private val _usuarioId = mutableStateOf<Long?>(null)
    val usuarioId: Long? get() = _usuarioId.value

    private val _mascotaId = mutableStateOf<Long?>(null)
    val mascotaId: Long? get() = _mascotaId.value

    //variable para traer todas las citas
    val citas = repositorio.allCitas.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )


    //funcion para traer las citas por usuario
    fun getCitasByUsuario(idUsuario: Long): StateFlow<List<Cita>>{
        _usuarioId.value = idUsuario
        return repositorio.getCitasByVeterinario(idUsuario).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    //funcion para traer las citas por mascota
    fun getCitasByMascota(idMascota: Long): StateFlow<List<Cita>>{
        _mascotaId.value = idMascota
        return repositorio.getCitasByMascota(idMascota).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    //funcion insertar
    fun agregarCitas(
        fecha: Date,
        mascotaId: Long,
        usuarioId: Long

    ) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            mensaje = null,
            error = null
        )
        try {
            val cita = Cita(
                fecha = fecha,
                mascotaId = mascotaId,
                usuarioId = usuarioId
            )
            repositorio.insertarCita(cita)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                mensaje = "cita agregada correctamente",
                error = null
            )
        }
        catch (e: Exception){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                mensaje = null,
                error = "No se pudo crear la cita ${e.message}"
            )
        }
    }

    //funcion eliminar
    fun eliminarCita(cita: Cita) = viewModelScope.launch {
        repositorio.eliminarCita(cita)
    }

    //funcion actualizar
    fun actualizarCita(cita: Cita) = viewModelScope.launch {
        repositorio.actualizarCita(cita)
    }

    //funcion limpiar
    fun limpiarMensaje(){
        _uiState.value = _uiState.value.copy(
            mensaje = null,
            error = null
        )
    }
}

data class CitaUiState(
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
)