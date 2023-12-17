import android.content.Context
import androidx.lifecycle.asFlow
import com.example.hydrinker.screens.dataStore
import com.example.hydrinker.services.HydrationViewModel
import com.example.hydrinker.services.ProfileService
import kotlinx.coroutines.flow.first
import java.util.Date

class ScoreService(context: Context, val hydrationViewModel: HydrationViewModel) {
    private val profileService = ProfileService(context.dataStore)

    suspend fun calculateDailyScore(date: Date): Double {
        var score = 0.0

        val dailyGoal = profileService.readProfile().dailyGoal
        if (dailyGoal > 0) {
            // Convert LiveData to Flow and collect the data
            val hydrationDataForDay =
                hydrationViewModel.getHydrationDataForDate(date).asFlow().first()
            val totalDrinkAmount = hydrationDataForDay.sumOf { it.amountInMillilitres }
            score = (totalDrinkAmount.toDouble() / dailyGoal) * 100
        }

        return if (score > 100) 100.0 else score
    }
}