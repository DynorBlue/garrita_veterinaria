package org.utl.veterinaria_garrita.ui.componentes.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.utl.veterinaria_garrita.db.model.Rol

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarUsuarioDialog(
    onDismiss: () -> Unit,
    onConfirm: (nombreUsuario: String, contrasena: String, edad: Int,
            genero: Char, rol: Rol) -> Unit
) {

    var nombreUsuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var rolSeleccionado by remember { mutableStateOf(Rol.ASISTENTE) }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Agregar usuario") },
        text = {
            Column {
                OutlinedTextField(
                    value = nombreUsuario,
                    onValueChange = { nombreUsuario = it },
                    label = { Text("Nombre de usuario") }
                )
                OutlinedTextField(
                    value = contrasena,
                    onValueChange = { contrasena = it },
                    label = { Text("Contraseña") }
                )
                OutlinedTextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") }
                )
OutlinedTextField(
                    value = genero,
                    onValueChange = { if (it.length <= 1) genero = it.uppercase() },
                    label = { Text("Género (H/M)") },
                    placeholder = { Text("H o M") }
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = rolSeleccionado.name,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text("Rol") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Rol.values().forEach { rol ->
                            DropdownMenuItem(
                                text = { Text(rol.name) },
                                onClick = {
                                    rolSeleccionado = rol
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
confirmButton = {
            TextButton(
                onClick = {
                    val edadInt = edad.toIntOrNull()
                    val generoChar = genero.firstOrNull()?.uppercaseChar()
                    
                    if (nombreUsuario.isNotBlank() && 
                        contrasena.isNotBlank() && 
                        edadInt != null && 
                        generoChar != null && 
                        (generoChar == 'H' || generoChar == 'M')) {
                        onConfirm(nombreUsuario, contrasena, edadInt, generoChar, rolSeleccionado)
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