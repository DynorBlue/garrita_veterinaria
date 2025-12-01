package org.utl.veterinaria_garrita.ui.componentes.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.utl.veterinaria_garrita.db.model.Cliente
import org.utl.veterinaria_garrita.ui.theme.BlancoHumo
import org.utl.veterinaria_garrita.ui.theme.Negro

@Composable
fun ClienteCard(cliente: Cliente, onClick: () -> Unit){
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
        Row (modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nombre: ${cliente.nombreCompleto}",
                color = Negro,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Id: ${cliente.idCliente}",
                color = Negro,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
