package org.utl.veterinaria_garrita.db.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.utl.veterinaria_garrita.db.dao.CitaDao
import org.utl.veterinaria_garrita.db.dao.ClienteDao
import org.utl.veterinaria_garrita.db.dao.InventarioDao
import org.utl.veterinaria_garrita.db.dao.MascotaDao
import org.utl.veterinaria_garrita.db.dao.UsuarioDao
import org.utl.veterinaria_garrita.db.model.Cita
import org.utl.veterinaria_garrita.db.model.Cliente
import org.utl.veterinaria_garrita.db.model.Inventario
import org.utl.veterinaria_garrita.db.model.Mascota
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
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}