package cl.duoc.proyecto_pago_flex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compromisos_pago")
data class CompromisoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val empresa: String,
    val monto: Double,
    val fechaVencimiento: String,
    val estado: String // "PENDIENTE" o "PAGADO"
)