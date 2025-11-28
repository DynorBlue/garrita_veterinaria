package org.utl.veterinaria_garrita.db.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.Date

@Entity(
    tableName = "cita", foreignKeys = [ForeignKey(
        entity = Mascota::class,
        parentColumns = ["idMascota"],
        childColumns = ["mascotaId"],
        onDelete = ForeignKey.Companion.CASCADE
    ), ForeignKey(
        entity = Usuario::class,
        parentColumns = ["idUsuario"],
        childColumns = ["usuarioId"],
        onDelete = ForeignKey.Companion.CASCADE
    )], indices = [Index("mascotaId"), Index("usuarioId")]
)
data class Cita(
    val idCita: Long = 0,
    val fecha: Date,
    val mascotaId: Long,
    val usuarioId: Long
)