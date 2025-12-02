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
import org.utl.veterinaria_garrita.db.model.Cliente
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonEditar
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonEliminar
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonPrimario
import org.utl.veterinaria_garrita.ui.componentes.card.ClienteCard
import org.utl.veterinaria_garrita.ui.componentes.dialogs.AgregarClienteDialog
import org.utl.veterinaria_garrita.ui.componentes.dialogs.EditarClienteDialog

@Composable
fun ClientesScreen(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    var showAgregarDialog by remember { mutableStateOf(false) }
    var showEditarDialog by remember { mutableStateOf(false) }
    var clienteSeleccionado by remember { mutableStateOf<Cliente?>(null) }
    
    // Datos de prueba
    val clientesMock = listOf(
        Cliente(
            idCliente = 1,
            nombreCompleto = "Roberto Sánchez",
            usuarioId = 1
        ),
        Cliente(
            idCliente = 2,
            nombreCompleto = "Laura Fernández",
            usuarioId = 2
        ),
        Cliente(
            idCliente = 3,
            nombreCompleto = "Miguel Ángel Torres",
            usuarioId = 3
        ),
        Cliente(
            idCliente = 4,
            nombreCompleto = "Carmen Rodríguez",
            usuarioId = 4
        ),
        Cliente(
            idCliente = 5,
            nombreCompleto = "Diego Herrera",
            usuarioId = 1
        )
    )

    EstructuraPrincipalPantallas(
        title = "Clientes",
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
                    texto = "Agregar Cliente",
                    onClick = { showAgregarDialog = true },
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                // Lista de clientes
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(clientesMock) { cliente ->
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
                                // Card del cliente
                                ClienteCard(
                                    cliente = cliente,
                                    onClick = {
                                        clienteSeleccionado = cliente
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
                                            clienteSeleccionado = cliente
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
    
    // Diálogo de agregar cliente
    if (showAgregarDialog) {
        AgregarClienteDialog(
            onDismiss = { showAgregarDialog = false },
            onConfirm = { nombreCompleto, usuarioId ->
                // Lógica para agregar cliente
                showAgregarDialog = false
            }
        )
    }
    
    // Diálogo de editar cliente
    if (showEditarDialog && clienteSeleccionado != null) {
        EditarClienteDialog(
            cliente = clienteSeleccionado!!,
            onDismiss = { 
                showEditarDialog = false
                clienteSeleccionado = null
            },
            onConfirm = { clienteActualizado ->
                // Lógica para actualizar cliente
                showEditarDialog = false
                clienteSeleccionado = null
            }
        )
    }
}