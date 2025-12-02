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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.unit.dp
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
    var showAgregarDialog by remember { mutableStateOf(false) }
    var showEditarDialog by remember { mutableStateOf(false) }
    var mascotaSeleccionada by remember { mutableStateOf<Mascota?>(null) }
    
    // Datos de prueba
    val mascotasMock = listOf(
        Mascota(
            idMascota = 1,
            nombreMascota = "Firulais",
            raza = "Labrador",
            edad = 3,
            peso = 25.5,
            clienteId = 1
        ),
        Mascota(
            idMascota = 2,
            nombreMascota = "Michi",
            raza = "Siames",
            edad = 2,
            peso = 4.2,
            clienteId = 2
        ),
        Mascota(
            idMascota = 3,
            nombreMascota = "Rex",
            raza = "Pastor Alemán",
            edad = 5,
            peso = 32.0,
            clienteId = 3
        ),
        Mascota(
            idMascota = 4,
            nombreMascota = "Luna",
            raza = "Golden Retriever",
            edad = 4,
            peso = 28.5,
            clienteId = 4
        ),
        Mascota(
            idMascota = 5,
            nombreMascota = "Pelusa",
            raza = "Persa",
            edad = 1,
            peso = 3.8,
            clienteId = 5
        ),
        Mascota(
            idMascota = 6,
            nombreMascota = "Max",
            raza = "Bulldog",
            edad = 6,
            peso = 22.0,
            clienteId = 1
        )
    )

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
                    items(mascotasMock) { mascota ->
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
                                        onClick = { /* Lógica para eliminar */ },
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
                // Lógica para agregar mascota
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
                // Lógica para actualizar mascota
                showEditarDialog = false
                mascotaSeleccionada = null
            }
        )
    }
}