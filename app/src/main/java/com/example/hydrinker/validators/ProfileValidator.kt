package com.example.hydrinker.validators

class ProfileValidator {
    companion object {
        fun validateName(name: String): Boolean = name.isNotEmpty()

        fun validateWeight(weight: String): Boolean =
            weight.isNotEmpty() && weight.toDoubleOrNull() != null

        fun validateAge(age: String): Boolean =
            age.isNotEmpty() && age.toIntOrNull() != null

        fun validateDailyGoal(dailyGoal: String): Boolean =
            dailyGoal.isNotEmpty() && dailyGoal.toDoubleOrNull() != null

        fun validateDrinkSize(drinkSize: String): Boolean =
            drinkSize.isNotEmpty() && drinkSize.toDoubleOrNull() != null

    }
}
