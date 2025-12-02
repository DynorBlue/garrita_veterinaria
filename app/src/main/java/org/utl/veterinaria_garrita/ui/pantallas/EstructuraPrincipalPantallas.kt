package org.utl.veterinaria_garrita.ui.pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstructuraPrincipalPantallas(
    title: String,
    currentScreen: String,
    onNavigate: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Veterinaria Garrita",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                NavigationDrawerItem(
                    label = { Text("Home") },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    selected = currentScreen == "home",
                    onClick = {
                        onNavigate("home")
                        scope.launch { drawerState.close() }
                    }
                )
                
                NavigationDrawerItem(
                    label = { Text("Usuarios") },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Usuarios") },
                    selected = currentScreen == "usuarios",
                    onClick = {
                        onNavigate("usuarios")
                        scope.launch { drawerState.close() }
                    }
                )
                
                NavigationDrawerItem(
                    label = { Text("Clientes") },
                    icon = { Icon(Icons.Default.People, contentDescription = "Clientes") },
                    selected = currentScreen == "clientes",
                    onClick = {
                        onNavigate("clientes")
                        scope.launch { drawerState.close() }
                    }
                )
                
                NavigationDrawerItem(
                    label = { Text("Mascotas") },
                    icon = { Icon(Icons.Default.Pets, contentDescription = "Mascotas") },
                    selected = currentScreen == "mascotas",
                    onClick = {
                        onNavigate("mascotas")
                        scope.launch { drawerState.close() }
                    }
                )
                
                NavigationDrawerItem(
                    label = { Text("Citas") },
                    icon = { Icon(Icons.Default.Event, contentDescription = "Citas") },
                    selected = currentScreen == "citas",
                    onClick = {
                        onNavigate("citas")
                        scope.launch { drawerState.close() }
                    }
                )
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(title) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Abrir Menú")
                        }
                    }
                )
            }
        ) { paddingValues ->
            content(paddingValues)
        }
    }
}