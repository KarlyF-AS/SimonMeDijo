package com.dam.simonmedijo.data

import com.dam.simonmedijo.Record

interface HistorialRecord {
    suspend fun guardarRecord(record: Record)
    suspend fun cargarRecord(): Record?
    suspend fun obtenerRondaRecord(): Int
}
