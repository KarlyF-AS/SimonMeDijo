package com.dam.simonmedijo.data

import com.dam.simonmedijo.Record

/**
 * Interfaz para manejar el historial de récords del juego.
 *
 * Esta interfaz define las operaciones básicas para **guardar, cargar y consultar**
 * el récord máximo. Permite cambiar la implementación sin afectar el resto de la app,
 * por ejemplo, usando Room, SharedPreferences o cualquier otra forma de almacenamiento.
 */
interface HistorialRecord {

    /**
     * Guarda un récord en el almacenamiento.
     * @param record La información del récord a guardar, incluyendo la ronda máxima
     * alcanzada y la fecha en que se logró.
     */
    suspend fun guardarRecord(record: Record)

    /**
     * Carga el récord actual desde el almacenamiento.
     * @return [Record]? → devuelve el récord si existe, o null si no hay ninguno.
     */
    suspend fun cargarRecord(): Record?

    /**
     * Obtiene únicamente la ronda máxima del récord guardado.
     * @return [Int] → la ronda máxima, o 0 si no hay récord guardado.
     */
    suspend fun obtenerRondaRecord(): Int
}
