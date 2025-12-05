package com.dam.simonmedijo

import android.content.Context

interface  HistorialRecord{
    fun guardasRecord(record: Record)
    fun cargarRecord(): Record?
}

class RecordSharedP (context: Context) : HistorialRecord {
    private val sharedPrefs = context.getSharedPreferences(
        "com.dam.simonmedijo.RECORD_PREFS",
        Context.MODE_PRIVATE
    )

    override fun guardasRecord(record: Record) {
        sharedPrefs.edit().apply {
            putInt("max_ronda", record.maxRonda)
            putString("fecha_texto", record.fechaTexto)
            putLong("tiempoMS", record.tiempoMS)
            apply()
        }
    }

    override fun cargarRecord(): Record? {
        TODO("Not yet implemented")
    }
}