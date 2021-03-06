package com.darrenfinch.mymealplanner.common.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.screens.allfoods.controller.AllFoodsFragment
import com.darrenfinch.mymealplanner.screens.allmeals.controller.AllMealsFragment
import com.darrenfinch.mymealplanner.screens.foodform.controller.FoodFormFragment
import com.darrenfinch.mymealplanner.screens.mealform.controller.MealFormFragment
import com.darrenfinch.mymealplanner.screens.mealplan.controller.MealPlanFragment
import com.darrenfinch.mymealplanner.screens.mealplanform.controller.MealPlanFormFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions

class ScreensNavigator(private val navController: FragNavController) {

    private val rootFragmentListener = object : FragNavController.RootFragmentListener {
        override val numberOfRootFragments = 3

        override fun getRootFragment(index: Int): Fragment {
            return when (index) {
                FragNavController.TAB1 -> MealPlanFragment.newInstance()
                FragNavController.TAB2 -> AllMealsFragment.newInstance()
                FragNavController.TAB3 -> AllFoodsFragment.newInstance()
                else -> throw IllegalStateException("Wrong tab index: $index")
            }
        }
    }

    private val enterAnim = android.R.anim.fade_in
    private val exitAnim = android.R.anim.fade_out

    fun init(savedInstanceState: Bundle?) {
        navController.rootFragmentListener = rootFragmentListener
        navController.initialize(FragNavController.TAB1, savedInstanceState)
    }

    fun onSaveInstanceState(savedInstanceState: Bundle?) {
        navController.onSaveInstanceState(savedInstanceState)
    }

    fun goBack(): Boolean {
        return if (navController.isRootFragment) {
            false
        } else {
            navController.popFragment(
                FragNavTransactionOptions.newBuilder()
                    .customAnimations(enterAnim, exitAnim, enterAnim, exitAnim)
                    .build()
            )
            true
        }
    }

    fun switchTab(index: Int) {
        val enterAnimation = if(navController.currentStackIndex < index) android.R.anim.slide_out_right else android.R.anim.slide_in_left
        val exitAnimation = if(navController.currentStackIndex < index) android.R.anim.slide_in_left else android.R.anim.slide_out_right
        navController.switchTab(index,
            FragNavTransactionOptions.newBuilder()
            .customAnimations(enterAnim, exitAnim, enterAnim, exitAnim)
            .build()
        )
    }

    fun toFoodFormScreen(foodId: Int) {
        navController.pushFragment(
            FoodFormFragment.newInstance(foodId),
            FragNavTransactionOptions.newBuilder()
                .customAnimations(enterAnim, exitAnim)
                .build()
        )
    }

    fun toMealFormScreen(
        mealId: Int
    ) {
        navController.pushFragment(
            MealFormFragment.newInstance(mealId),
            FragNavTransactionOptions.newBuilder()
                .customAnimations(enterAnim, exitAnim)
                .build()
        )
    }

    fun toMealPlanFormScreen() {
        navController.pushFragment(
            MealPlanFormFragment.newInstance(),
            FragNavTransactionOptions.newBuilder()
                .customAnimations(enterAnim, exitAnim)
                .build()
        )
    }
}