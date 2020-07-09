package com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.controller

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.darrenfinch.mymealplanner.common.ScreensNavigator
import com.darrenfinch.mymealplanner.domain.selectmealfoodquantity.view.SelectMealFoodViewMvc
import com.darrenfinch.mymealplanner.domain.usecases.GetSingleFoodUseCase
import com.darrenfinch.mymealplanner.model.data.MealFood

class SelectMealFoodQuantityController(
    private val foodId: Int,
    private val screensNavigator: ScreensNavigator,
    private val getSingleFoodUseCase: GetSingleFoodUseCase
) : SelectMealFoodViewMvc.Listener {

    private lateinit var viewMvc: SelectMealFoodViewMvc

    fun bindView(viewMvc: SelectMealFoodViewMvc) {
        this.viewMvc = viewMvc
    }

    fun fetchFood(viewLifecycleOwner: LifecycleOwner) {
        getSingleFoodUseCase.fetchFood(foodId).observe(viewLifecycleOwner, Observer {
            viewMvc.bindFood(it)
        })
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun onMealFoodQuantityChosen(mealFood: MealFood) {
//        updateCurrentlyEditedMealWithNewMealFood(mealFood)
        screensNavigator.navigateFromSelectMealFoodQuantityScreenToAddEditMealScreen(mealFood)
    }

//    private fun updateCurrentlyEditedMealWithNewMealFood(mealFood: MealFood) {
//        val mealFoods = viewModel.currentlyEditedMeal.foods.toMutableList()
//        mealFoods.add(mealFood)
//        viewModel.currentlyEditedMeal.foods = mealFoods
//    }
}
