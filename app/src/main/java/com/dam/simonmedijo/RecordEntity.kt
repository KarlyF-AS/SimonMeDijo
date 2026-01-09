package com.dam.simonmedijo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa un registro del récord máximo del juego.
 *
 * Esta clase es una **entidad de Room**, lo que significa que cada instancia
 * se corresponde con una fila en la tabla de la base de datos llamada "record_table".
 *
 * Solo hay un registro en la tabla (id = 1), que guarda la información del récord.
 * @property id Identificador único del registro. Siempre es 1, ya que solo se guarda un récord.
 * @property maxRonda Número máximo de rondas alcanzadas en el juego.
 * @property fechaTexto Fecha en formato legible (dd/MM/yyyy HH:mm:ss) en la que se obtuvo el récord.
 * @property tiempoMS Timestamp en milisegundos correspondiente al momento del récord.
 */
@Entity(tableName = "record_table")
data class RecordEntity(
    @PrimaryKey val id: Int = 1,  // Siempre habrá un solo registro
    val maxRonda: Int,
    val fechaTexto: String,
    val tiempoMS: Long
)
