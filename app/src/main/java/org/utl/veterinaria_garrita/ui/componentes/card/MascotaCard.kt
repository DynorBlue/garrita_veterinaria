package org.utl.veterinaria_garrita.ui.componentes.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.utl.veterinaria_garrita.db.model.Mascota
import org.utl.veterinaria_garrita.ui.theme.BlancoHumo
import org.utl.veterinaria_garrita.ui.theme.Negro

@Composable
fun MascotaCard(mascota: Mascota, onClick: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, color = Negro),
        colors = CardDefaults.cardColors(
            containerColor = BlancoHumo
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nombre: ${mascota.nombreMascota}",
                color = Negro,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Id: ${mascota.idMascota}",
                color = Negro,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Raza: ${mascota.raza}",
                color = Negro,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Edad: ${mascota.edad}",
                color = Negro,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Peso (KG): ${mascota.peso}",
                color = Negro,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "ID Dueño: ${mascota.clienteId}",
                color = Negro,
                style = MaterialTheme.typography.bodyMedium
            )

        }
    }
}
