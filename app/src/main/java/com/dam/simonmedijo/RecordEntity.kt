package com.dam.simonmedijo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_table")
data class RecordEntity(
    @PrimaryKey val id: Int = 1,  // Siempre habrá un solo registro
    val maxRonda: Int,
    val fechaTexto: String,
    val tiempoMS: Long
)
