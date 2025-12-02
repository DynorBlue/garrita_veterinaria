package org.utl.veterinaria_garrita.ui.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import org.utl.veterinaria_garrita.db.data.AppDataBase
import org.utl.veterinaria_garrita.db.repositorio.CitaRepositorio
import org.utl.veterinaria_garrita.db.repositorio.ClienteRepositorio
import org.utl.veterinaria_garrita.db.repositorio.MascotaRepositorio
import org.utl.veterinaria_garrita.db.repositorio.UsuarioRepositorio
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.unit.sp
import org.utl.veterinaria_garrita.ui.theme.AzulClaro
import org.utl.veterinaria_garrita.ui.theme.BlancoHumo

@Composable
fun Home(
    currentScreen: String,
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current
    val database = AppDataBase.getDataBase(context)
    
    // Crear repositorios
    val usuarioRepositorio = remember { UsuarioRepositorio(database.usuarioDao()) }
    val clienteRepositorio = remember { ClienteRepositorio(database.clienteDao()) }
    val mascotaRepositorio = remember { MascotaRepositorio(database.mascotaDao()) }
    val citaRepositorio = remember { CitaRepositorio(database.citaDao()) }
    
    // Obtener datos de la base de datos
    val usuarios by usuarioRepositorio.allUsuarios.collectAsState(initial = emptyList())
    val clientes by clienteRepositorio.allClientes.collectAsState(initial = emptyList())
    val mascotas by mascotaRepositorio.allMascotas.collectAsState(initial = emptyList())
    val citas by citaRepositorio.allCitas.collectAsState(initial = emptyList())
    
    // Calcular citas de hoy
    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val citasHoy = citas.filter { cita ->
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cita.fecha) == today
    }
    
    EstructuraPrincipalPantallas(
        title = "Veterinaria Garrita",
        currentScreen = currentScreen,
        onNavigate = onNavigate
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Título de bienvenida
                Text(
                    text = "Bienvenido a Veterinaria Garrita",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Text(
                    text = "Sistema de Gestión Veterinaria",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Tarjetas de estadísticas
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Usuarios",
                        value = usuarios.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Clientes",
                        value = clientes.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        title = "Mascotas",
                        value = mascotas.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "Citas Hoy",
                        value = citasHoy.size.toString(),
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(32.dp))
    }
}


    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = AzulClaro
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
