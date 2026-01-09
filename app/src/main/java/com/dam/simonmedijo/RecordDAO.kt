package com.dam.simonmedijo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * DAO para la entidad [RecordEntity].
 *
 * Define las operaciones de acceso a la base de datos relacionadas con el récord máximo
 * del juego. Esta interfaz le dice a Room **cómo leer y escribir** en
 * la tabla `record_table`.
 */
@Dao
interface RecordDao {

    /**
     * Obtiene el récord actual de la base de datos.
     * @return [RecordEntity]? → devuelve la fila del récord si existe, o null si no hay ningún registro.
     */
    @Query("SELECT * FROM record_table WHERE id = 1")
    suspend fun getRecord(): RecordEntity?

    /**
     * Guarda un récord en la base de datos.
     * Si ya existe un registro con el mismo [RecordEntity.id], lo reemplaza gracias a
     * [OnConflictStrategy.REPLACE].
     *
     * @param record La entidad [RecordEntity] a guardar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecord(record: RecordEntity)
}
