package com.dam.simonmedijo

import android.content.Context

/**
 * Interfaz para gestionar el almacenamiento del récord del jugador.
 * Permite guardarlo, cargarlo y obtener la ronda máxima registrada.
 */
interface HistorialRecord {
    fun guardarRecord(record: Record)
    fun cargarRecord(): Record?
    fun obtenerRondaRecord(): Int
}

/**
 * Implementación de HistorialRecord que usa SharedPreferences
 * para almacenar el récord de forma persistente.
 */
class RecordSharedP(context: Context) : HistorialRecord {

    /** SharedPreferences donde se guardan los valores del récord. */
    private val sharedPrefs = context.getSharedPreferences(
        "com.dam.simonmedijo.RECORD_PREFS",
        Context.MODE_PRIVATE
    )

    /**
     * Guarda en SharedPreferences el récord con su ronda máxima,
     * fecha en texto y marca temporal.
     */
    override fun guardarRecord(record: Record) {
        sharedPrefs.edit().apply {
            putInt("max_ronda", record.maxRonda)
            putString("fecha_texto", record.fechaTexto)
            putLong("tiempoMS", record.tiempoMS)
            apply()
        }
    }

    /**
     * Carga el récord guardado en SharedPreferences.
     * @return Record si existe, o null si nunca se ha guardado.
     */
    override fun cargarRecord(): Record? {
        val maxRonda = sharedPrefs.getInt("max_ronda", -1)
        if (maxRonda == -1) return null

        return Record(
            maxRonda = maxRonda,
            fechaTexto = sharedPrefs.getString("fecha_texto", "") ?: "",
            tiempoMS = sharedPrefs.getLong("tiempoMS", 0)
        )
    }

    /**
     * Devuelve únicamente la ronda máxima almacenada como récord.
     */
    override fun obtenerRondaRecord(): Int {
        return sharedPrefs.getInt("max_ronda", 0)
    }
}
