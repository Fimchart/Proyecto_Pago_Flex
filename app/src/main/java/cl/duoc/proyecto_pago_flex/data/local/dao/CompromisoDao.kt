package cl.duoc.proyecto_pago_flex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.duoc.proyecto_pago_flex.data.local.entity.CompromisoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompromisoDao {

    @Query("SELECT * FROM compromisos_pago ORDER BY id DESC")
    fun obtenerTodosCompromisos(): Flow<List<CompromisoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCompromiso(compromiso: CompromisoEntity)

    @Query("UPDATE compromisos_pago SET estado = :nuevoEstado WHERE id = :id")
    suspend fun actualizarEstado(id: Int, nuevoEstado: String)

    @Query("UPDATE compromisos_pago SET empresa = :empresa, monto = :monto, fechaVencimiento = :fecha WHERE id = :id")
    suspend fun actualizarCompromiso(id: Int, empresa: String, monto: Double, fecha: String)
}