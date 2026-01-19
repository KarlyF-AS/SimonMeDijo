package com.dam.simonmedijo.data

import android.content.Context
import com.dam.simonmedijo.Record

/**
 * Implementación de [HistorialRecord] usando MongoDB Realm.
 *
 * Esta clase adapta el repository de Realm a la interfaz HistorialRecord,
 * proporcionando una abstracción limpia para cambiar la implementación de
 * persistencia en el futuro (Room, SharedPreferences, etc.).
 *
 * @param context Contexto de la aplicación, necesario para inicializar Realm
 */
class RecordRealmImpl(context: Context) : HistorialRecord {

    private val repository = RecordRealmRepository(context)

    /**
     * Guarda un récord en Realm mediante el repository.
     *
     * @param record El objeto Record con maxRonda, fechaTexto y tiempoMS
     */
    override suspend fun guardarRecord(record: Record) {
        repository.guardarRecord(record)
    }

    /**
     * Carga el récord actual desde Realm.
     *
     * @return El Record guardado, o null si no existe
     */
    override suspend fun cargarRecord(): Record? {
        return repository.obtenerRecordActual()
    }

    /**
     * Obtiene la ronda máxima del récord guardado.
     *
     * @return La ronda máxima, o 0 si no hay récord
     */
    override suspend fun obtenerRondaRecord(): Int {
        return repository.obtenerRondaMaxima()
    }
}
