package org.utl.veterinaria_garrita.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.utl.veterinaria_garrita.db.data.AppDataBase
import org.utl.veterinaria_garrita.db.repositorio.MascotaRepositorio
import org.utl.veterinaria_garrita.db.viewModel.MascotaViewModel
import org.utl.veterinaria_garrita.db.viewModel.MascotaViewModelFactory
import org.utl.veterinaria_garrita.db.model.Mascota
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonEditar
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonEliminar
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonPrimario
import org.utl.veterinaria_garrita.ui.componentes.card.MascotaCard
import org.utl.veterinaria_garrita.ui.componentes.dialogs.AgregarMascotaDialog
import org.utl.veterinaria_garrita.ui.componentes.dialogs.EditarMascotaDialog

@Composable
fun MascotasScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val database = AppDataBase.getDataBase(context)
    val repositorio = remember { MascotaRepositorio(database.mascotaDao()) }
    val viewModel: MascotaViewModel = viewModel(factory = MascotaViewModelFactory(repositorio))
    
    var showAgregarDialog by remember { mutableStateOf(false) }
    var showEditarDialog by remember { mutableStateOf(false) }
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    
    val mascotas by viewModel.mascotas.collectAsState(initial = emptyList())
    val uiState by viewModel.uiState
    val scope = rememberCoroutineScope()

    EstructuraPrincipalPantallas(
        title = "Mascotas",
        currentScreen = currentScreen,
        onNavigate = onNavigate
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Botón de agregar
                BotonPrimario(
                    texto = "Agregar Mascota",
                    onClick = { showAgregarDialog = true },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Lista de mascotas
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(mascotas) { mascota ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                // Card de la mascota
                                MascotaCard(
                                    mascota = mascota,
                                    onClick = {
                                        mascotaSeleccionada = mascota
                                        showEditarDialog = true
                                    }
                                )
                                
                                Spacer(modifier = Modifier.height(12.dp))
                                
                                // Botones de acción
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    BotonEditar(
                                        texto = "Editar",
                                        onClick = {
                                            mascotaSeleccionada = mascota
                                            showEditarDialog = true
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                    BotonEliminar(
                                        texto = "Eliminar",
                                        onClick = { 
                                            scope.launch {
                                                viewModel.eliminarMascota(mascota)
                                            }
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
        }
    }
}


                    }
                }
            }
        }
    }
    
    // Diálogo de agregar mascota
    if (showAgregarDialog) {
        AgregarMascotaDialog(
            onDismiss = { showAgregarDialog = false },
            onConfirm = { nombreMascota, raza, edad, peso, clienteId ->
                scope.launch {
                    viewModel.agregarMascota(clienteId, nombreMascota, raza, edad, peso)
                }
                showAgregarDialog = false
            }
        )
    }
    
    // Diálogo de editar mascota
    if (showEditarDialog && mascotaSeleccionada != null) {
        EditarMascotaDialog(
            mascota = mascotaSeleccionada!!,
            onDismiss = { 
                showEditarDialog = false
                mascotaSeleccionada = null
            },
            onConfirm = { mascotaActualizada ->
                scope.launch {
                    viewModel.actualizarMascota(mascotaActualizada)
                }
                showEditarDialog = false
                mascotaSeleccionada = null
            }
        )
    }
    
    // Mostrar mensajes de estado
    uiState.mensaje?.let { mensaje ->
        androidx.compose.material3.Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                androidx.compose.material3.TextButton(onClick = { viewModel.limpiarMensaje() }) {
                    androidx.compose.material3.Text("OK")
                }
            }
        ) {
            androidx.compose.material3.Text(mensaje)
        }
    }
    
    uiState.error?.let { error ->
        androidx.compose.material3.Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                androidx.compose.material3.TextButton(onClick = { viewModel.limpiarMensaje() }) {
                    androidx.compose.material3.Text("OK")
                }
            }
        ) {
            androidx.compose.material3.Text(error)
        }
    }
}