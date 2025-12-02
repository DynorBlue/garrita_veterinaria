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
fun AgregarInventarioDialog(
    onDismiss: () -> Unit,
    onConfirm: (nombreProducto: String, descripcion: String, cantidad: Int, precio: Double, categoria: String) -> Unit
) {
    var nombreProducto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar item al inventario") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombreProducto,
                    onValueChange = { nombreProducto = it },
                    label = { Text("Nombre del producto") },
                    isError = nombreProducto.isBlank() && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    isError = descripcion.isBlank() && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad") },
                    isError = (cantidad.toIntOrNull() == null || cantidad.toIntOrNull()!! <= 0) && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    isError = (precio.toDoubleOrNull() == null || precio.toDoubleOrNull()!! <= 0) && errorMessage.isNotEmpty()
                )
                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoría") },
                    isError = categoria.isBlank() && errorMessage.isNotEmpty()
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
                    val cantidadInt = cantidad.toIntOrNull()
                    val precioDouble = precio.toDoubleOrNull()
                    
                    when {
                        nombreProducto.isBlank() -> errorMessage = "El nombre del producto es requerido"
                        descripcion.isBlank() -> errorMessage = "La descripción es requerida"
                        cantidadInt == null || cantidadInt <= 0 -> errorMessage = "La cantidad debe ser un número mayor a 0"
                        precioDouble == null || precioDouble <= 0 -> errorMessage = "El precio debe ser un número mayor a 0"
                        categoria.isBlank() -> errorMessage = "La categoría es requerida"
                        else -> {
                            onConfirm(nombreProducto, descripcion, cantidadInt, precioDouble, categoria)
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