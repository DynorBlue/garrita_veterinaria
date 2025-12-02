package org.utl.veterinaria_garrita.ui.componentes.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AgregarClienteDialog(
    onDismiss: () -> Unit,
    onConfirm: (nombreCompleto: String, usuarioId: Long) -> Unit
) {
    var nombreCompleto by remember { mutableStateOf("") }
    var usuarioId by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar cliente") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombreCompleto,
                    onValueChange = { nombreCompleto = it },
                    label = { Text("Nombre completo") },
                    isError = nombreCompleto.isBlank() && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = usuarioId,
                    onValueChange = { usuarioId = it },
                    label = { Text("ID de usuario") },
                    isError = usuarioId.toLongOrNull() == null && errorMessage.isNotEmpty()
                )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val usuarioIdLong = usuarioId.toLongOrNull()
                    
                    if (nombreCompleto.isBlank()) {
                        errorMessage = "El nombre completo es requerido"
                    } else if (usuarioIdLong == null) {
                        errorMessage = "El ID de usuario debe ser un número válido"
                    } else if (usuarioIdLong <= 0) {
                        errorMessage = "El ID de usuario debe ser mayor a 0"
                    } else {
                        onConfirm(nombreCompleto, usuarioIdLong)
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}