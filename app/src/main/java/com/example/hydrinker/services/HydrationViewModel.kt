package com.example.hydrinker.services

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hydrinker.database.HydrationData
import com.example.hydrinker.database.HydrinkerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

class HydrationViewModel(val context: Context) : ViewModel() {
    private val hydrinkerDatabase = HydrinkerDatabase.getDatabase(context)

    fun addDrink(drinkSize: Int) {
        viewModelScope.launch {
            sendDrinkSizeToDatabase(drinkSize, hydrinkerDatabase)
        }
    }

    private suspend fun sendDrinkSizeToDatabase(drinkSize: Int, database: HydrinkerDatabase) {
        val hydrationData = HydrationData(
            date = Date(),
            amountInMillilitres = drinkSize
        )
        database.hydrationDao().insert(hydrationData)

        Toast.makeText(context, "${hydrationData.amountInMillilitres}ml was tracked!", Toast.LENGTH_SHORT).show()
    }

    fun getHydrationData(): LiveData<List<HydrationData>> {
        return hydrinkerDatabase.hydrationDao().getAllHydrationData()
    }

    fun getLastWeekHydrationData(): LiveData<List<HydrationData>> {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -7)
        val startDate = calendar.timeInMillis
        return hydrinkerDatabase.hydrationDao().getLastWeekHydrationData(startDate)
    }

    fun seedDataForPastWeek() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAllHydrationData()

            val calendar = Calendar.getInstance()
            for (i in 1..7) {
                calendar.time = Date()
                calendar.add(Calendar.DAY_OF_YEAR, -i)
                val randomDrinkSize = Random.nextInt(200, 3000) // Random drink size
                val hydrationData = HydrationData(
                    date = calendar.time,
                    amountInMillilitres = randomDrinkSize
                )
                hydrinkerDatabase.hydrationDao().insert(hydrationData)
            }
        }
    }

    private suspend fun deleteAllHydrationData() {
        hydrinkerDatabase.hydrationDao().deleteAll()
    }
}

class HydrationViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HydrationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HydrationViewModel(context) as T
        }
        throw IllegalArgumentException("ViewModel not found")
    }
}
