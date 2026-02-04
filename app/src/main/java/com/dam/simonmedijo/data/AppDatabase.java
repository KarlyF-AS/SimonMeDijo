package com.dam.simonmedijo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dam.simonmedijo.data.dao.RecordDao
import com.dam.simonmedijo.data.entity.RecordEntity

@Database(
        entities = [RecordEntity::class],
version = 1,
exportSchema = false
        )
abstract class AppDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "simon_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
