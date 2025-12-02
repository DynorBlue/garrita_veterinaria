package org.utl.veterinaria_garrita.ui.componentes.lists

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.utl.veterinaria_garrita.db.model.Inventario
import org.utl.veterinaria_garrita.ui.componentes.card.InventarioCard

@Composable
fun InventarioList(
    inventario: List<Inventario>,
    onClick: (Inventario) -> Unit
){
LazyColumn {
        items(inventario) { item ->
            InventarioCard(inventario = item, onClick = { onClick(item) })
        }
    }
}