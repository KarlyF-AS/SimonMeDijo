package com.dam.simonmedijo.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

/**
 * Modelo de datos para MongoDB Realm que representa un récord del juego.
 *
 * Esta clase es una **entidad de Realm**, persistida automáticamente en la base de datos
 * NoSQL embebida. Realm maneja la encriptación y sincronización automáticamente.
 *
 * @property id Identificador único del registro. Siempre es 1, ya que solo se guarda un récord máximo.
 * @property maxRonda Número máximo de rondas alcanzadas en el juego.
 * @property fechaTexto Fecha en formato legible (dd/MM/yyyy HH:mm:ss) en la que se obtuvo el récord.
 * @property tiempoMS Timestamp en milisegundos correspondiente al momento del récord.
 */
@Suppress("unused") // Realm reflection
class RealmRecordModel : RealmObject {
    @PrimaryKey
    var id: Int = 1  // Siempre habrá un solo registro

    var maxRonda: Int = 0
    var fechaTexto: String = ""
    var tiempoMS: Long = 0
}
