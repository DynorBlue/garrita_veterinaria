package org.utl.veterinaria_garrita.ui.componentes.lists

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import org.utl.veterinaria_garrita.db.model.Mascota
import org.utl.veterinaria_garrita.ui.componentes.card.MascotaCard

@Composable
fun MascotaList(
    mascotas: List<Mascota>,
    onClick: (Mascota) -> Unit
) {
LazyColumn {
        items(mascotas) { mascota ->
            MascotaCard(mascota = mascota, onClick = { onClick(mascota) })
        }
    }
}