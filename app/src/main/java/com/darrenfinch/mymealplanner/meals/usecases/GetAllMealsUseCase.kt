package com.darrenfinch.mymealplanner.meals.usecases

import com.darrenfinch.mymealplanner.common.extensions.parallelMap
import com.darrenfinch.mymealplanner.meals.models.mappers.dbMealToMeal
import com.darrenfinch.mymealplanner.meals.models.mappers.mealToUiMeal
import com.darrenfinch.mymealplanner.meals.models.presentation.UiMeal
import com.darrenfinch.mymealplanner.model.MainRepository
import kotlin.coroutines.coroutineContext

class GetAllMealsUseCase(private val repository: MainRepository) {

    suspend fun getAllMeals(): List<UiMeal> {
        return repository.getAllMeals().parallelMap(coroutineContext) { dbMeal ->
            val dbMealFoods = repository.getMealFoodsForMeal(dbMeal.id)
            val dbFoodReferences = dbMealFoods.map { dbMealFood -> repository.getFood(dbMealFood.foodId) }
            mealToUiMeal(dbMealToMeal(dbMeal, dbMealFoods, dbFoodReferences))
        }
    }

}