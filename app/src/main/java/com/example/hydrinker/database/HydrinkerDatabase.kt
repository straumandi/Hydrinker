package com.example.hydrinker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [HydrationData::class], version = 3)
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
                )
                    .addMigrations(MIGRATION_1_2)
                    .addMigrations(MIGRATION_2_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE hydration_data RENAME TO temp_hydration_data")
        db.execSQL(
            """
            CREATE TABLE hydration_data (
                id INTEGER PRIMARY KEY NOT NULL,
                date INTEGER NOT NULL,
                amountInMillilitres INTEGER NOT NULL
            )
        """.trimIndent()
        )
        db.execSQL(
            """
            INSERT INTO hydration_data (id, date, amountInMillilitres)
            SELECT id, date, CAST(amount AS INTEGER) FROM temp_hydration_data
        """.trimIndent()
        )
        db.execSQL("DROP TABLE temp_hydration_data")
    }
}

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE hydration_data RENAME TO temp_hydration_data")
        db.execSQL(
            """
            CREATE TABLE hydration_data (
                id INTEGER PRIMARY KEY NOT NULL,
                date INTEGER NOT NULL,
                amountInMillilitres INTEGER NOT NULL
            )
        """.trimIndent()
        )
        db.execSQL(
            """
            INSERT INTO hydration_data (id, date, amountInMillilitres)
            SELECT id, date, amountInMillilitres FROM temp_hydration_data
        """.trimIndent()
        )
        db.execSQL("DROP TABLE temp_hydration_data")
    }
}