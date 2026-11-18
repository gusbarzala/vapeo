package com.example.vapeogame

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PuntajeVapeoEntity::class], version = 1, exportSchema = false)
abstract class AppVapeoDatabase : RoomDatabase() {

    abstract fun puntajeDao(): PuntajeVapeoDao

    companion object {
        @Volatile
        private var INSTANCE: AppVapeoDatabase? = null

        fun getDatabase(context: Context): AppVapeoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppVapeoDatabase::class.java,
                    "vapeo_games_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}