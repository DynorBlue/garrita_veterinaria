package org.utl.veterinaria_garrita.ui.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.utl.veterinaria_garrita.R
import org.utl.veterinaria_garrita.ui.componentes.buttons.BotonPrimario
import org.utl.veterinaria_garrita.ui.theme.AzulFuerte
import org.utl.veterinaria_garrita.ui.theme.Blanco
import org.utl.veterinaria_garrita.ui.theme.Gris
import org.utl.veterinaria_garrita.ui.theme.Negro

@Composable
fun InicioSesion(
    onLoginSuccess: () -> Unit = {}
) {
//variables para los textfield
    var usuarioInput by remember { mutableStateOf("") }
    var contrasenaIput by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        //titulos
        Text(
            text = "Veterinaria",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Gris,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Garrita",
            fontSize = 70.sp,
            fontWeight = FontWeight.ExtraBold,
            color = AzulFuerte,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(40.dp))
        //cajas de texto para el usuario
        OutlinedTextField(
            value = usuarioInput,
            onValueChange = { usuarioInput = it},
            label = {
                Text(text = "Nombre de usuario",
                    color = Negro
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        //caja de texto de la contraseña
        OutlinedTextField(
            value = contrasenaIput,
            onValueChange = {
                contrasenaIput = it
            },
            label = {
                Text(text = "Contraseña",
                    color = Negro)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            visualTransformation = PasswordVisualTransformation() // para convertir la contraseña a puntitos
        )
        Spacer(modifier = Modifier.height(30.dp))
        BotonPrimario(texto = "Iniciar Sesion", onClick = {
            // Lógica de autenticación básica - se mejorará con base de datos
            if (usuarioInput == "admin" && contrasenaIput == "admin123") {
                onLoginSuccess()
            }
        })
        Spacer(modifier = Modifier.height(30.dp))
        //imagen
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = "Logo de garrita",
            modifier = Modifier
                .width(500.dp)
                .height(500.dp)
        )

    }

}
@Composable
@Preview(showBackground = true)
fun inicioSesionVista() {
    InicioSesion()
}