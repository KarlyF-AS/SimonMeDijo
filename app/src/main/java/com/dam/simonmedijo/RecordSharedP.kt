package com.dam.simonmedijo

import android.content.Context

interface  HistorialRecord{
    fun guardarRecord(record: Record)
    fun cargarRecord(): Record?
    fun obtenerRondaRecord(): Int
}

class RecordSharedP (context: Context) : HistorialRecord {
    private val sharedPrefs = context.getSharedPreferences(
        "com.dam.simonmedijo.RECORD_PREFS",
        Context.MODE_PRIVATE
    )

    override fun guardarRecord(record: Record) {
        sharedPrefs.edit().apply {
            putInt("max_ronda", record.maxRonda)
            putString("fecha_texto", record.fechaTexto)
            putLong("tiempoMS", record.tiempoMS)
            apply()
        }
    }


    override fun cargarRecord(): Record? {
        val maxRonda = sharedPrefs.getInt("max_ronda", -1)
        if (maxRonda == -1) return null

            return Record(
                maxRonda = maxRonda,
                fechaTexto = sharedPrefs.getString("fecha_texto", "") ?: "",
                tiempoMS = sharedPrefs.getLong("tiempoMS", 0)
            )
        }
    override fun obtenerRondaRecord(): Int {
        return sharedPrefs.getInt("max_ronda", 0)
    }
}