package com.example.kanopys.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kanopys.di.ApplicationScope

@ApplicationScope
@Database(entities = [UserDatabase::class, MovieDatabase::class], version = 10, exportSchema = false)
@TypeConverters(KanopysConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): ProfileUserDao

    companion object {
        private val lock = Any()
        private var instance: AppDatabase? = null
        private const val DB_NAME = "profile.db"

        fun getInstance(application: Application): AppDatabase {
            instance?.let { return it }
            synchronized(lock) {
                instance?.let { return it }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = db
                return db
            }
        }
    }
}