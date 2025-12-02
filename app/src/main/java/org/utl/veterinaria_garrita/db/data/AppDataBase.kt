package org.utl.veterinaria_garrita.db.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.utl.veterinaria_garrita.db.dao.CitaDao
import org.utl.veterinaria_garrita.db.dao.ClienteDao
import org.utl.veterinaria_garrita.db.dao.InventarioDao
import org.utl.veterinaria_garrita.db.dao.MascotaDao
import org.utl.veterinaria_garrita.db.dao.UsuarioDao
import org.utl.veterinaria_garrita.db.model.Cita
import org.utl.veterinaria_garrita.db.model.Cliente
import org.utl.veterinaria_garrita.db.model.Inventario
import org.utl.veterinaria_garrita.db.model.Mascota
import org.utl.veterinaria_garrita.db.model.Rol
import org.utl.veterinaria_garrita.db.model.Usuario

@Database(
    entities = [Usuario::class, Cliente::class, Mascota::class, Cita::class, Inventario::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ConvertidorFecha::class, ConvertidorRol::class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun usuarioDao(): UsuarioDao
    abstract fun clienteDao(): ClienteDao

    abstract fun mascotaDao(): MascotaDao

    abstract fun citaDao(): CitaDao

    abstract fun inventarioDao(): InventarioDao

    companion object{

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "garrita_dataBase"
                ).addCallback(DatabaseCallback())
                .build()
                INSTANCE = instance
                
                // Verificar y crear usuario admin si no existe
                runBlocking {
                    try {
                        val usuarioDao = instance.usuarioDao()
                        val adminCount = usuarioDao.adminExists()
                        if (adminCount == 0) {
                            val adminUser = org.utl.veterinaria_garrita.db.model.Usuario(
                                nombreUsuario = "admin",
                                contrasena = "admin123",
                                edad = 30,
                                genero = 'H',
                                rol = org.utl.veterinaria_garrita.db.model.Rol.ADMIN
                            )
                            usuarioDao.insertUsuario(adminUser)
                        }
                    } catch (e: Exception) {
                        // Manejar error si ya existe
                    }
                }
                
                return instance
            }
        }
        
        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Insertar usuario admin por defecto
                db.execSQL(
                    "INSERT INTO usuario (nombreUsuario, contrasena, edad, genero, rol) VALUES " +
                    "('admin', 'admin123', 30, 'H', 'ADMIN')"
                )
            }
            
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Verificar y crear usuario admin si no existe (para bases de datos existentes)
                // Esta es una solución alternativa si la base de datos ya existe
            }
        }
    }
}