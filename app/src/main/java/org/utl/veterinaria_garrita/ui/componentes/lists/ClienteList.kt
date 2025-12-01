package org.utl.veterinaria_garrita.ui.componentes.lists

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.utl.veterinaria_garrita.db.model.Cliente
import org.utl.veterinaria_garrita.ui.componentes.card.ClienteCard

@Composable
fun ClienteList(
    clientes: List<Cliente>,
    onClick: (Cliente) -> Unit
){
LazyColumn {
        items(clientes) { cliente ->
            ClienteCard(cliente = cliente, onClick = { onClick(cliente) })
        }
    }
}