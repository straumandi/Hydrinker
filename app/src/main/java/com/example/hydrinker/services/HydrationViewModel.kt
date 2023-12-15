package com.example.hydrinker.services

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hydrinker.database.HydrationData
import com.example.hydrinker.database.HydrinkerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Date

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

    suspend fun getHydrationData(): List<HydrationData> {
        return viewModelScope.async(Dispatchers.IO) {
            val data = hydrinkerDatabase.hydrationDao().getAllHydrationData()
            println(data.takeLast(5))
            data
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
