# 🧭 Sistema de Navegación - Veterinaria Garrita

## 📁 Estructura de Archivos

```
ui/
├── navegacion/
│   └── AppNavigation.kt          # Configuración principal de navegación
├── pantallas/
│   ├── Home.kt                   # Dashboard principal
│   ├── UsuariosScreen.kt         # Gestión de usuarios
│   ├── ClientesScreen.kt         # Gestión de clientes
│   ├── MascotasScreen.kt         # Gestión de mascotas
│   ├── CitasScreen.kt            # Gestión de citas
│   ├── EstructuraPrincipalPantallas.kt  # Componente de UI común
│   └── InicioSesion.kt           # Pantalla de login
```

## 🚀 Configuración de Navegación

### AppNavigation.kt
- **NavHost**: Gestiona las rutas de la aplicación
- **Rutas definidas**:
  - `home` → Pantalla principal (dashboard)
  - `usuarios` → Gestión de usuarios
  - `clientes` → Gestión de clientes
  - `mascotas` → Gestión de mascotas
  - `citas` → Gestión de citas

### MainActivity.kt
- **Punto de entrada**: Inicia `AppNavigation()`
- **Tema aplicado**: `Veterinaria_garritaTheme`
- **Surface**: Contenedor principal con fondo del tema

## 🎯 Flujo de Navegación

### 1. Inicio de Aplicación
```
MainActivity → AppNavigation → Home (startDestination)
```

### 2. Navegación entre Pantallas
```
Home → [Menú Lateral] → Usuarios/Clientes/Mascotas/Citas
```

### 3. Implementación del Menú
- **EstructuraPrincipalPantallas**: Componente con menú lateral
- **NavigationDrawer**: Menú deslizante con iconos
- **onNavigate**: Callback para cambiar de pantalla

## 📱 Componentes de Navegación

### EstructuraPrincipalPantallas.kt
```kotlin
@Composable
fun EstructuraPrincipalPantallas(
    title: String,              // Título de la pantalla actual
    currentScreen: String,       // Pantalla actual seleccionada
    onNavigate: (String) -> Unit, // Callback de navegación
    content: @Composable (PaddingValues) -> Unit
)
```

**Características:**
- ✅ Menú lateral con 5 opciones
- ✅ Iconos para cada sección
- ✅ Estado de selección visual
- ✅ Cierre automático al seleccionar

### AppNavigation.kt
```kotlin
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
)
```

**Características:**
- ✅ NavHost con rutas definidas
- ✅ Navegación tipo Single-Activity
- ✅ Integración con Compose Navigation
- ✅ Callbacks de navegación conectados

## 🔄 Integración de Pantallas

### Cada pantalla recibe:
```kotlin
@Composable
fun [Pantalla]Screen(
    currentScreen: String,       // Para resaltar en menú
    onNavigate: (String) -> Unit // Para navegar a otras pantallas
)
```

### Flujo de datos:
1. **Usuario toca opción del menú**
2. **onNavigate()** llama a **navController.navigate()**
3. **NavHost** cambia a la pantalla correspondiente
4. **Nueva pantalla** recibe **currentScreen** actualizado

## 🎨 Diseño y UX

### Menú Lateral
- **Home** 🏠 - Dashboard con estadísticas
- **Usuarios** 👤 - Gestión de usuarios y roles
- **Clientes** 👥 - Gestión de clientes
- **Mascotas** 🐾 - Gestión de mascotas
- **Citas** 📅 - Gestión de citas médicas

### Estados Visuales
- **Seleccionado**: Color primario del tema
- **No seleccionado**: Color por defecto
- **Iconos**: Material Icons consistentes

## 🔧 Configuración Técnica

### Dependencias (build.gradle.kts)
```kotlin
val nav_version = "2.9.5"
implementation("androidx.navigation:navigation-compose:$nav_version")
```

### Imports Clave
```kotlin
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
```

## 🚀 Próximos Pasos

### Para integrar lógica de negocio:
1. **ViewModels**: Conectar con cada pantalla
2. **Repositorios**: Implementar operaciones CRUD
3. **Base de datos**: Conectar Room con navegación
4. **Estados**: Manejar loading/error/success

### Para mejorar UX:
1. **Animaciones**: Transiciones entre pantallas
2. **Deep Links**: Navegación directa a secciones
3. **Back Navigation**: Manejar botón de atrás
4. **State Restoration**: Mantener estado al rotar

## ✅ Estado Actual

- ✅ **Navegación básica**: Funcional y conectada
- ✅ **Menú lateral**: Integrado con todas las pantallas
- ✅ **Estructura**: Componentes reutilizables
- ✅ **Compilación**: Sin errores
- ✅ **Diseño**: Consistente y moderno

**Listo para:** Integración con ViewModels y lógica de negocio real.