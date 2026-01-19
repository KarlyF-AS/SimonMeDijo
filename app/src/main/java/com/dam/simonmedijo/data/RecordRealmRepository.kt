package com.dam.simonmedijo.data

import android.content.Context
import com.dam.simonmedijo.Record
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository para gestionar la persistencia de récords usando MongoDB Realm.
 *
 * Esta clase proporciona métodos CRUD (Create, Read, Update, Delete) para almacenar
 * y recuperar récords del juego en una base de datos NoSQL embebida (Realm).
 * Todas las operaciones son asíncronas para no bloquear la UI thread.
 *
 * Ventajas de Realm:
 * - Encriptación automática de datos
 * - Base de datos NoSQL eficiente
 * - Transacciones atómicas
 * - Sin necesidad de backend externo
 *
 * @param context Contexto de la aplicación, usado para inicializar Realm
 */
class RecordRealmRepository(context: Context) {

    private lateinit var realm: Realm

    init {
        // Configurar Realm con la aplicación de contexto
        val config = RealmConfiguration.create(
            schema = setOf(RealmRecordModel::class)
        )
        realm = Realm.open(config)
    }

    /**
     * Guarda un récord en Realm. Si ya existe un récord con id=1, lo actualiza.
     * La operación es atómica y thread-safe.
     *
     * @param record Objeto [Record] con maxRonda, fechaTexto y tiempoMS
     * @return true si la operación fue exitosa, false en caso contrario
     * @throws Exception si ocurre un error durante la escritura
     */
    suspend fun guardarRecord(record: Record): Boolean = withContext(Dispatchers.IO) {
        try {
            realm.write {
                // Buscar si ya existe un récord
                val existente = query<RealmRecordModel>("id == 1").first()

                if (existente != null) {
                    // Actualizar el existente
                    existente.maxRonda = record.maxRonda
                    existente.fechaTexto = record.fechaTexto
                    existente.tiempoMS = record.tiempoMS
                } else {
                    // Crear uno nuevo
                    copyToRealm(
                        RealmRecordModel().apply {
                            id = 1
                            maxRonda = record.maxRonda
                            fechaTexto = record.fechaTexto
                            tiempoMS = record.tiempoMS
                        }
                    )
                }
            }
            true
        } catch (e: Exception) {
            android.util.Log.e("RecordRealmRepository", "Error al guardar récord: ${e.message}")
            false
        }
    }

    /**
     * Carga el récord actual desde Realm.
     *
     * @return Un objeto [Record] si existe, o null si la base de datos está vacía
     */
    suspend fun obtenerRecordActual(): Record? = withContext(Dispatchers.IO) {
        try {
            val realmRecord = realm.query<RealmRecordModel>("id == 1").first()?.copy()

            realmRecord?.let {
                Record(
                    maxRonda = it.maxRonda,
                    fechaTexto = it.fechaTexto,
                    tiempoMS = it.tiempoMS
                )
            }
        } catch (e: Exception) {
            android.util.Log.e("RecordRealmRepository", "Error al cargar récord: ${e.message}")
            null
        }
    }

    /**
     * Obtiene únicamente la ronda máxima del récord guardado.
     *
     * @return La ronda máxima, o 0 si no hay récord guardado
     */
    suspend fun obtenerRondaMaxima(): Int = withContext(Dispatchers.IO) {
        try {
            realm.query<RealmRecordModel>("id == 1").first()?.maxRonda ?: 0
        } catch (e: Exception) {
            android.util.Log.e("RecordRealmRepository", "Error al obtener ronda máxima: ${e.message}")
            0
        }
    }

    /**
     * Obtiene el historial completo de récords (solo 1 en este caso).
     * Útil para auditoría o estadísticas futuras.
     *
     * @return Lista con todos los récords guardados (siempre será 0 o 1 elemento)
     */
    suspend fun obtenerHistorialCompleto(): List<Record> = withContext(Dispatchers.IO) {
        try {
            realm.query<RealmRecordModel>().find().map { realmRecord ->
                Record(
                    maxRonda = realmRecord.maxRonda,
                    fechaTexto = realmRecord.fechaTexto,
                    tiempoMS = realmRecord.tiempoMS
                )
            }
        } catch (e: Exception) {
            android.util.Log.e("RecordRealmRepository", "Error al obtener historial: ${e.message}")
            emptyList()
        }
    }

    /**
     * Elimina todos los récords de la base de datos.
     * Útil para testing o reset de datos.
     *
     * @return true si la operación fue exitosa, false en caso contrario
     */
    suspend fun eliminarTodos(): Boolean = withContext(Dispatchers.IO) {
        try {
            realm.write {
                val records = query<RealmRecordModel>().find()
                delete(records)
            }
            true
        } catch (e: Exception) {
            android.util.Log.e("RecordRealmRepository", "Error al eliminar récords: ${e.message}")
            false
        }
    }

    /**
     * Cierra la conexión con Realm. Llamar cuando la app se destruye.
     */
    fun cerrar() {
        if (this::realm.isInitialized && !realm.isClosed()) {
            realm.close()
        }
    }
}
