package com.example.hydrinker.services

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hydrinker.database.HydrationData
import com.example.hydrinker.database.HydrinkerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Date

class HydrationViewModel(context: Context) : ViewModel() {
    private val hydrinkerDatabase = HydrinkerDatabase.getDatabase(context)

    fun addDrink(drinkSize: Double) {
        viewModelScope.launch {
            sendDrinkSizeToDatabase(drinkSize, hydrinkerDatabase)
        }
    }

    private suspend fun sendDrinkSizeToDatabase(drinkSize: Double, database: HydrinkerDatabase) {
        val hydrationData = HydrationData(
            date = Date(),
            amount = drinkSize
        )
        println(hydrationData)

        database.hydrationDao().insert(hydrationData)
    }

    suspend fun getHydrationData(): List<HydrationData> {
        return viewModelScope.async(Dispatchers.IO) {
            hydrinkerDatabase.hydrationDao().getAllHydrationData()
        }.await()
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
