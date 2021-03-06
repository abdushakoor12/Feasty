package com.darrenfinch.mymealplanner.screens.mealplan.controller

import androidx.lifecycle.LifecycleOwner
import com.darrenfinch.mymealplanner.common.controllers.BaseController
import com.darrenfinch.mymealplanner.common.dialogs.DialogsManager
import com.darrenfinch.mymealplanner.common.misc.ControllerSavedState
import com.darrenfinch.mymealplanner.common.navigation.ScreensNavigator
import com.darrenfinch.mymealplanner.screens.mealplan.view.MealPlanViewMvc
import com.darrenfinch.mymealplanner.mealplans.models.presentation.UiMealPlanMeal
import com.darrenfinch.mymealplanner.mealplans.usecases.*

// TODO: Reimplement this controller
class MealPlanController(
    private val getAllMealPlansUseCase: GetAllMealPlansUseCase,
    private val getMealsForMealPlanUseCase: GetMealsForMealPlanUseCase,
    private val insertMealPlanMealUseCase: InsertMealPlanMealUseCase,
    private val deleteMealPlanUseCase: DeleteMealPlanUseCase,
    private val deleteMealPlanMealUseCase: DeleteMealPlanMealUseCase,
    private val screensNavigator: ScreensNavigator,
    private val dialogsManager: DialogsManager
) : BaseController, MealPlanViewMvc.Listener {

    class SavedState : ControllerSavedState

    private lateinit var viewMvc: MealPlanViewMvc

    fun bindView(viewMvc: MealPlanViewMvc) {
        this.viewMvc = viewMvc
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    fun fetchAllMealPlans(viewLifecycleOwner: LifecycleOwner) {
        // TODO: Fetch all meal plans, and when notified, update spinner and update list of meal plan meals
    }

    override fun onMealPlanSelected(index: Int) {
        // TODO: Get meals for meal plan with index
    }

    override fun onAddNewMealPlanClicked() {
        screensNavigator.toMealPlanFormScreen()
    }
    override fun onDeleteMealPlanClicked() {
        // TODO: Update meal plan selection spinner and list of meal plan meals
    }

    override fun onAddNewMealPlanMealClicked() {
        dialogsManager.showSelectMealPlanMealDialog()
    }
    override fun onDeleteMealPlanMealClicked(mealPlanMeal: UiMealPlanMeal) {
        // TODO: Delete meal and update list of meals
    }

    override fun restoreState(state: ControllerSavedState) {}
    override fun getState(): ControllerSavedState {
        return SavedState()
    }

    // TODO: Get result from select meal plan meal dialog
}