package cl.duoc.proyecto_pago_flex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cl.duoc.proyecto_pago_flex.data.local.dao.CompromisoDao
import cl.duoc.proyecto_pago_flex.data.local.entity.CompromisoEntity

@Database(entities = [CompromisoEntity::class], version = 1, exportSchema = false)
abstract class PagoFlexDatabase : RoomDatabase() {

    abstract fun compromisoDao(): CompromisoDao

    companion object {
        @Volatile
        private var INSTANCE: PagoFlexDatabase? = null

        fun getDatabase(context: Context): PagoFlexDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PagoFlexDatabase::class.java,
                    "pagoflex_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}