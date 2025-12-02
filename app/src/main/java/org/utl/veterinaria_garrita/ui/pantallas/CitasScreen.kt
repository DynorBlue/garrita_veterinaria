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
import org.utl.veterinaria_garrita.db.repositorio.CitaRepositorio
import org.utl.veterinaria_garrita.db.viewModel.CitaViewModel
import org.utl.veterinaria_garrita.db.viewModel.CitaViewModelFactory
import org.utl.veterinaria_garrita.db.model.Cita
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonEditar
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonEliminar
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonPrimario
import org.utl.veterinaria_garrita.ui.componentes.card.CitaCard
import org.utl.veterinaria_garrita.ui.componentes.dialogs.AgregarCitaDialog
import org.utl.veterinaria_garrita.ui.componentes.dialogs.EditarCitaDialog
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CitasScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val database = AppDataBase.getDataBase(context)
    val repositorio = remember { CitaRepositorio(database.citaDao()) }
    val viewModel: CitaViewModel = viewModel(factory = CitaViewModelFactory(repositorio))
    
    var showAgregarDialog by remember { mutableStateOf(false) }
    var showEditarDialog by remember { mutableStateOf(false) }
    var citaSeleccionada by remember { mutableStateOf<Cita?>(null) }
    
    val citas by viewModel.citas.collectAsState(initial = emptyList())
    val uiState by viewModel.uiState
    val scope = rememberCoroutineScope()

    EstructuraPrincipalPantallas(
        title = "Citas",
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
                    texto = "Agregar Cita",
                    onClick = { showAgregarDialog = true },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Lista de citas
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(citas) { cita ->
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
                                // Card de la cita
                                CitaCard(
                                    cita = cita,
                                    onClick = {
                                        citaSeleccionada = cita
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
                                            citaSeleccionada = cita
                                            showEditarDialog = true
                                        },
                                        modifier = Modifier.weight(1f)
                                    )
                                    BotonEliminar(
                                        texto = "Eliminar",
                                        onClick = { 
                                            scope.launch {
                                                viewModel.eliminarCita(cita)
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
    
    // Diálogo de agregar cita
    if (showAgregarDialog) {
        AgregarCitaDialog(
            onDismiss = { showAgregarDialog = false },
            onConfirm = { fecha, mascotaId, usuarioId ->
                scope.launch {
                    viewModel.agregarCitas(fecha, mascotaId, usuarioId)
                }
                showAgregarDialog = false
            }
        )
    }
    
    // Diálogo de editar cita
    if (showEditarDialog && citaSeleccionada != null) {
        EditarCitaDialog(
            cita = citaSeleccionada!!,
            onDismiss = { 
                showEditarDialog = false
                citaSeleccionada = null
            },
            onConfirm = { citaActualizada ->
                scope.launch {
                    viewModel.actualizarCita(citaActualizada)
                }
                showEditarDialog = false
                citaSeleccionada = null
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