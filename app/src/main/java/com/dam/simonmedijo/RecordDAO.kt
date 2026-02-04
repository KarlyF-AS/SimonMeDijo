package com.dam.simonmedijo.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

/**
 * Implementación de SQLite para le gestión de records de la app
 */
@Dao
interface RecordDao {

    @Insert
    suspend fun insert(record: RecordsEntity)

    @Query("SELECT * FROM records ORDER BY maxRonda DESC, tiempoMS ASC LIMIT 10")
    suspend fun getTop10(): List<RecordsEntity>

    @Query("DELETE FROM records WHERE id NOT IN (SELECT id FROM records ORDER BY maxRonda DESC, tiempoMS ASC LIMIT 10)")
    suspend fun trimToTop10()

    @Transaction
    suspend fun insertAndTrim(record: RecordsEntity) {
        insert(record)
        trimToTop10()
    }

    @Query("DELETE FROM records")
    suspend fun deleteAll()

//    @Query("SELECT * FROM records ORDER BY maxRonda DESC, tiemposMS ASC LIMIT 10")
//    suspend fun getTop10(): List<RecordsEntity>

//    @Query("DELETE FROM records WHERE ID NOT IN (SELECT id FROM records ORDER BY maxRonda DESC, tiempoMS ASC LIMIT 10)")
//
//    @Transaction
//    suspend fun insertAndTrim(record: RecordsEntity){
//        insert(record)
//        trimToTop10()
//    }

//    @Query("DELETE FROM records")
//    suspend fun deleteAll()
}
