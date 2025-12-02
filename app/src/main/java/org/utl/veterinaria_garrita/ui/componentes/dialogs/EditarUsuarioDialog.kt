package org.utl.veterinaria_garrita.ui.componentes.dialogs
/*
@Composable
fun EditUsuarioDialog(
    usuario: Usuario,                   //  Recibe el usuario a editar
    onDismiss: () -> Unit,
    onConfirm: (Usuario) -> Unit        //  Devuelve el usuario editado
) {
    var nombre by remember { mutableStateOf(usuario.nombreUsuario) }
    var rol by remember { mutableStateOf(usuario.rol) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar usuario") },
        text = {
            Column {

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre de usuario") }
                )

                OutlinedTextField(
                    value = rol,
                    onValueChange = { rol = it },
                    label = { Text("Rol") }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Crear un nuevo objeto actualizado
                    val usuarioActualizado = usuario.copy(
                        nombreUsuario = nombre,
                        rol = rol
                    )
                    onConfirm(usuarioActualizado)
                }
            ) {
                Text("Guardar cambios")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
*/