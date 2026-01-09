package com.dam.simonmedijo.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Clase que representa la base de datos de la aplicación usando Room.

 * Esta base de datos tiene una única tabla: [RecordEntity], que almacena
 * el récord máximo del juego "Simon Me Dijo".
 * @property recordDao Proporciona acceso a las operaciones de la tabla RecordEntity.
 * @see RecordEntity
 * @see RecordDao
 */
@Database(
    entities = [RecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Devuelve el DAO que permite realizar operaciones sobre la tabla [RecordEntity].
     *
     * Ejemplos de operaciones:
     * - Guardar un nuevo récord.
     * - Obtener el récord actual.
     *
     * @return instancia de [RecordDao]
     */
    abstract fun recordDao(): RecordDao
}
