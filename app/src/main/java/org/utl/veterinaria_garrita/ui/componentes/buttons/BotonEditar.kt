package org.utl.veterinaria_garrita.ui.componentes.buttons


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.utl.veterinaria_garrita.ui.theme.Blanco
import org.utl.veterinaria_garrita.ui.theme.VerdeAlerta

@Composable
fun BotonEditar(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = VerdeAlerta,
            contentColor = Blanco
        )
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelLarge
        )
    }
}