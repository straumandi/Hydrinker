package com.example.hydrinker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [HydrationData::class], version = 1)
@TypeConverters(Converters::class)
abstract class HydrinkerDatabase : RoomDatabase() {
    abstract fun hydrationDao(): HydrationDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: HydrinkerDatabase? = null

        fun getDatabase(context: Context): HydrinkerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, HydrinkerDatabase::class.java, "hydration_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
