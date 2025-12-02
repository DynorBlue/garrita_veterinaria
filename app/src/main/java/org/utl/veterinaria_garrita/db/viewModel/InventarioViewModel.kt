package org.utl.veterinaria_garrita.db.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.utl.veterinaria_garrita.db.model.Inventario
import org.utl.veterinaria_garrita.db.repositorio.InventarioRepositorio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InventarioViewModel (val repositorio: InventarioRepositorio): ViewModel(){
    private val _uiState = mutableStateOf(InventarioUiState())
    val uiState: State<InventarioUiState> = _uiState

    //variable para traer todos los items del inventario
    val inventario = repositorio.allInventario.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    //funcion para traer los items por categoria
    fun getInventarioByCategoria(categoria: String): StateFlow<List<Inventario>>{
        return repositorio.getInventarioByCategoria(categoria).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    //funcion insertar
    fun agregarInventario(
        nombreProducto: String,
        descripcion: String,
        cantidad: Int,
        precio: Double,
        categoria: String

    ) = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            mensaje = null,
            error = null
        )
        try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val fechaRegistro = dateFormat.format(Date())

            val inventarioItem = Inventario(
                nombreProducto = nombreProducto,
                descripcion = descripcion,
                cantidad = cantidad,
                precio = precio,
                categoria = categoria,
                fechaRegistro = fechaRegistro
            )
            repositorio.insertarInventario(inventarioItem)

            _uiState.value = _uiState.value.copy(
                isLoading = false,
                mensaje = "Item agregado correctamente",
                error = null
            )
        }
        catch (e: Exception){
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                mensaje = null,
                error = "No se pudo crear el item ${e.message}"
            )
        }
    }

    //funcion eliminar
    fun eliminarInventario(inventario: Inventario) = viewModelScope.launch {
        repositorio.eliminarInventario(inventario)
    }

    //funcion actualizar
    fun actualizarInventario(inventario: Inventario) = viewModelScope.launch {
        repositorio.actualizarInventario(inventario)
    }

    //funcion limpiar
    fun limpiarMensaje(){
        _uiState.value = _uiState.value.copy(
            mensaje = null,
            error = null
        )
    }
}

data class InventarioUiState(
    val isLoading: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null
)