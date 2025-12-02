package org.utl.veterinaria_garrita.ui.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.utl.veterinaria_garrita.ui.pantallas.CitasScreen
import org.utl.veterinaria_garrita.ui.pantallas.ClientesScreen
import org.utl.veterinaria_garrita.ui.pantallas.Home
import org.utl.veterinaria_garrita.ui.pantallas.MascotasScreen
import org.utl.veterinaria_garrita.ui.pantallas.UsuariosScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            Home(
                currentScreen = "home",
                onNavigate = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        
        composable("usuarios") {
            UsuariosScreen(
                currentScreen = "usuarios",
                onNavigate = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        
        composable("clientes") {
            ClientesScreen(
                currentScreen = "clientes",
                onNavigate = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        
        composable("mascotas") {
            MascotasScreen(
                currentScreen = "mascotas",
                onNavigate = { destination ->
                    navController.navigate(destination)
                }
            )
        }
        
        composable("citas") {
            CitasScreen(
                currentScreen = "citas",
                onNavigate = { destination ->
                    navController.navigate(destination)
                }
            )
        }
    }
}