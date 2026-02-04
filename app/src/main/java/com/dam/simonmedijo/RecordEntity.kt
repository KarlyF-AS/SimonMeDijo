package com.dam.simonmedijo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val maxRonda: Int,
    val fechaTexto: String,
    val tiempoMS: Long
)


