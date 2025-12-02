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
fun AgregarMascotaDialog(
    onDismiss: () -> Unit,
    onConfirm: (nombreMascota: String, raza: String, edad: Int, peso: Double, clienteId: Long) -> Unit
) {
    var nombreMascota by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var clienteId by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar mascota") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombreMascota,
                    onValueChange = { nombreMascota = it },
                    label = { Text("Nombre de la mascota") },
                    isError = nombreMascota.isBlank() && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = raza,
                    onValueChange = { raza = it },
                    label = { Text("Raza") },
                    isError = raza.isBlank() && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    isError = (edad.toIntOrNull() == null || edad.toIntOrNull()!! <= 0) && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = peso,
                    onValueChange = { peso = it },
                    label = { Text("Peso") },
                    isError = (peso.toDoubleOrNull() == null || peso.toDoubleOrNull()!! <= 0) && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = clienteId,
                    onValueChange = { clienteId = it },
                    label = { Text("ID del cliente") },
                    isError = (clienteId.toLongOrNull() == null || clienteId.toLongOrNull()!! <= 0) && errorMessage.isNotEmpty()
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
                    val edadInt = edad.toIntOrNull()
                    val pesoDouble = peso.toDoubleOrNull()
                    val clienteIdLong = clienteId.toLongOrNull()
                    
                    when {
                        nombreMascota.isBlank() -> errorMessage = "El nombre de la mascota es requerido"
                        raza.isBlank() -> errorMessage = "La raza es requerida"
                        edadInt == null || edadInt <= 0 -> errorMessage = "La edad debe ser un número mayor a 0"
                        pesoDouble == null || pesoDouble <= 0 -> errorMessage = "El peso debe ser un número mayor a 0"
                        clienteIdLong == null || clienteIdLong <= 0 -> errorMessage = "El ID del cliente debe ser un número mayor a 0"
                        else -> {
                            onConfirm(nombreMascota, raza, edadInt, pesoDouble, clienteIdLong)
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