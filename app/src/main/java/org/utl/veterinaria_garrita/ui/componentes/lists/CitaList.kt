package org.utl.veterinaria_garrita.ui.componentes.lists

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.utl.veterinaria_garrita.db.model.Cita
import org.utl.veterinaria_garrita.ui.componentes.card.CitaCard

@Composable
fun CitaList(
    citas: List<Cita>,
    onClick: (Cita) -> Unit
){
LazyColumn {
        items(citas) { cita ->
            CitaCard(cita = cita, onClick = { onClick(cita) })
        }
    }
}