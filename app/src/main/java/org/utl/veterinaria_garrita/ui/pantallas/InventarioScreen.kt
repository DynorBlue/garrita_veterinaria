package org.utl.veterinaria_garrita.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.utl.veterinaria_garrita.db.data.AppDataBase
import org.utl.veterinaria_garrita.db.model.Inventario
import org.utl.veterinaria_garrita.db.repositorio.InventarioRepositorio
import org.utl.veterinaria_garrita.db.viewModel.InventarioViewModel
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonEditar
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonEliminar
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonPrimario
import org.utl.veterinaria_garrita.ui.componentes.card.InventarioCard
import org.utl.veterinaria_garrita.ui.componentes.dialogs.AgregarInventarioDialog
import org.utl.veterinaria_garrita.ui.componentes.dialogs.EditarInventarioDialog
import org.utl.veterinaria_garrita.ui.theme.AzulClaro
import org.utl.veterinaria_garrita.ui.theme.RojoAlerta
import org.utl.veterinaria_garrita.ui.theme.VerdeAlerta

@Composable
fun InventarioScreen(
    database: AppDataBase
) {
    val repositorio = remember { InventarioRepositorio(database.inventarioDao()) }
    val viewModel: InventarioViewModel = viewModel(factory = InventarioViewModelFactory(repositorio))
    
    val inventario by viewModel.inventario.collectAsState(initial = emptyList())
    val uiState by viewModel.uiState
    
    var showAgregarDialog by remember { mutableStateOf(false) }
    var showEditarDialog by remember { mutableStateOf(false) }
    var showEliminarDialog by remember { mutableStateOf(false) }
    var selectedInventario by remember { mutableStateOf<Inventario?>(null) }

    // Mostrar mensajes
    LaunchedEffect(uiState.mensaje, uiState.error) {
        if (uiState.mensaje != null || uiState.error != null) {
            // Aquí podrías mostrar un Snackbar o Toast
            viewModel.limpiarMensaje()
        }
    }

    EstructuraPrincipalPantallas(title = "Inventario") { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Botón para agregar nuevo item
            BotonPrimario(
                texto = "Agregar Item",
                onClick = { showAgregarDialog = true }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de inventario
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(inventario) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            InventarioCard(
                                inventario = item,
                                onClick = {
                                    selectedInventario = item
                                    showEditarDialog = true
                                }
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                BotonEditar(
                                    texto = "Editar",
                                    onClick = {
                                        selectedInventario = item
                                        showEditarDialog = true
                                    }
                                )
                                BotonEliminar(
                                    texto = "Eliminar",
                                    onClick = {
                                        selectedInventario = item
                                        showEliminarDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo para agregar
    if (showAgregarDialog) {
        AgregarInventarioDialog(
            onDismiss = { showAgregarDialog = false },
            onConfirm = { nombre, descripcion, cantidad, precio, categoria ->
                viewModel.agregarInventario(nombre, descripcion, cantidad, precio, categoria)
                showAgregarDialog = false
            }
        )
    }

    // Diálogo para editar
    if (showEditarDialog && selectedInventario != null) {
        EditarInventarioDialog(
            inventario = selectedInventario!!,
            onDismiss = { 
                showEditarDialog = false
                selectedInventario = null
            },
            onConfirm = { nombre, descripcion, cantidad, precio, categoria ->
                val updatedInventario = selectedInventario!!.copy(
                    nombreProducto = nombre,
                    descripcion = descripcion,
                    cantidad = cantidad,
                    precio = precio,
                    categoria = categoria
                )
                viewModel.actualizarInventario(updatedInventario)
                showEditarDialog = false
                selectedInventario = null
            }
        )
    }

    // Diálogo de confirmación para eliminar
    if (showEliminarDialog && selectedInventario != null) {
        AlertDialog(
            onDismissRequest = { 
                showEliminarDialog = false
                selectedInventario = null
            },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar '${selectedInventario!!.nombreProducto}' del inventario?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.eliminarInventario(selectedInventario!!)
                        showEliminarDialog = false
                        selectedInventario = null
                    }
                ) {
                    Text("Eliminar", color = RojoAlerta)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        showEliminarDialog = false
                        selectedInventario = null
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

// Factory para el ViewModel
class InventarioViewModelFactory(private val repositorio: InventarioRepositorio) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventarioViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}