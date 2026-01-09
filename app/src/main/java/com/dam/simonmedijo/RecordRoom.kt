package com.dam.simonmedijo.data

import android.content.Context
import com.dam.simonmedijo.Record
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementación de [HistorialRecord] usando Room.
 * Esta clase se encarga de **guardar, cargar y consultar** el récord del juego
 * en una base de datos SQLite mediante Room. Todas las operaciones son asíncronas
 * usando coroutines para no bloquear la UI.
 *
 * @property db Instancia de [AppDatabase] que representa la base de datos.
 * @property dao DAO de Room para acceder a la tabla de récords ([RecordDao]).
 *
 * @param context Contexto de la aplicación, necesario para inicializar Room.
 */
class RecordRoom(context: Context) : HistorialRecord {

    // Instancia de la base de datos
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "simon_database"
    ).build()

    // Acceso al DAO de la base de datos
    private val dao = db.recordDao()

    /**
     * Guarda un récord en la base de datos.
     * Si ya existe un récord, se reemplaza porque usamos [OnConflictStrategy.REPLACE].
     * @param record Objeto [Record] con la ronda máxima, fecha y tiempo en milisegundos.
     */
    override suspend fun guardarRecord(record: Record) {
        withContext(Dispatchers.IO) {
            dao.saveRecord(
                RecordEntity(
                    id = 1, // siempre habrá un solo registro
                    maxRonda = record.maxRonda,
                    fechaTexto = record.fechaTexto,
                    tiempoMS = record.tiempoMS
                )
            )
        }
    }

    /**
     * Carga el récord actual desde la base de datos.
     * @return [Record]? → devuelve el récord si existe, o null si no hay ninguno.
     */
    override suspend fun cargarRecord(): Record? {
        return withContext(Dispatchers.IO) {
            dao.getRecord()?.let {
                Record(
                    maxRonda = it.maxRonda,
                    fechaTexto = it.fechaTexto,
                    tiempoMS = it.tiempoMS
                )
            }
        }
    }
    /**
     * Devuelve únicamente la ronda máxima del récord guardado.
     * @return [Int] → la ronda máxima, o 0 si no hay récord guardado.
     */
    override suspend fun obtenerRondaRecord(): Int {
        return cargarRecord()?.maxRonda ?: 0
    }
}
