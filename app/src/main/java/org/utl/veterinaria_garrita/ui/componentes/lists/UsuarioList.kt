package org.utl.veterinaria_garrita.ui.componentes.lists

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.utl.veterinaria_garrita.db.model.Usuario
import org.utl.veterinaria_garrita.ui.componentes.card.UsuarioCard

@Composable
fun UsuarioList(
    usuarios: List<Usuario>,
    onClick: (Usuario) -> Unit
) {
    LazyColumn {
        items(usuarios) { usuario ->
            UsuarioCard(usuario = usuario, onClick = { onClick(usuario) })
        }
    }
}