package com.example.hydrinker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import java.util.Date

@Entity(tableName = "hydration_data")
data class HydrationData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Date,
    val amountInMillilitres: Int
)

@Dao
interface HydrationDao {
    @Insert
    suspend fun insert(hydrationData: HydrationData)

    @Query("SELECT * FROM hydration_data ORDER BY date DESC")
    fun getAllHydrationData(): LiveData<List<HydrationData>>
}
