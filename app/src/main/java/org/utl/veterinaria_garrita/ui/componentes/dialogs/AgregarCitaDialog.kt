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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AgregarCitaDialog(
    onDismiss: () -> Unit,
    onConfirm: (fecha: Date, mascotaId: Long, usuarioId: Long) -> Unit
) {
    var fechaStr by remember { mutableStateOf("") }
    var mascotaId by remember { mutableStateOf("") }
    var usuarioId by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar cita") },
        text = {
            Column {
                OutlinedTextField(
                    value = fechaStr,
                    onValueChange = { fechaStr = it },
                    label = { Text("Fecha (dd/MM/yyyy HH:mm)") },
                    placeholder = { Text("Ej: 25/12/2024 14:30") },
                    isError = fechaStr.isBlank() && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = mascotaId,
                    onValueChange = { mascotaId = it },
                    label = { Text("ID de la mascota") },
                    isError = (mascotaId.toLongOrNull() == null || mascotaId.toLongOrNull()!! <= 0) && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = usuarioId,
                    onValueChange = { usuarioId = it },
                    label = { Text("ID del usuario") },
                    isError = (usuarioId.toLongOrNull() == null || usuarioId.toLongOrNull()!! <= 0) && errorMessage.isNotEmpty()
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
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    val fecha = try {
                        dateFormat.parse(fechaStr)
                    } catch (e: Exception) {
                        null
                    }
                    val mascotaIdLong = mascotaId.toLongOrNull()
                    val usuarioIdLong = usuarioId.toLongOrNull()
                    
                    when {
                        fechaStr.isBlank() -> errorMessage = "La fecha es requerida"
                        fecha == null -> errorMessage = "Formato de fecha inválido. Use dd/MM/yyyy HH:mm"
                        mascotaIdLong == null || mascotaIdLong <= 0 -> errorMessage = "El ID de la mascota debe ser un número mayor a 0"
                        usuarioIdLong == null || usuarioIdLong <= 0 -> errorMessage = "El ID del usuario debe ser un número mayor a 0"
                        else -> {
                            onConfirm(fecha, mascotaIdLong, usuarioIdLong)
                        }
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