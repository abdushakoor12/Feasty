package com.darrenfinch.mymealplanner.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.darrenfinch.mymealplanner.model.data.entities.MealPlan

@Dao
interface MealPlansDao {
    @Query("SELECT * FROM mealPlans")
    fun getMealPlans(): LiveData<List<DatabaseMealPlan>>

    @Query("SELECT * FROM mealPlans WHERE :id = id")
    suspend fun getMealPlan(id: Int): DatabaseMealPlan

    @Insert
    suspend fun insertMealPlan(mealPlan: DatabaseMealPlan)

    @Update
    suspend fun updateMealPlan(mealPlan: DatabaseMealPlan)

    @Delete
    suspend fun deleteMealPlan(mealPlan: DatabaseMealPlan)
}