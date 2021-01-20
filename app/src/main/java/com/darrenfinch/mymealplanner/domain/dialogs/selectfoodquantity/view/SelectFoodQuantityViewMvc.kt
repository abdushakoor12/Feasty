package com.darrenfinch.mymealplanner.domain.dialogs.selectfoodquantity.view

import android.app.Dialog
import com.darrenfinch.mymealplanner.common.views.ObservableViewMvc
import com.darrenfinch.mymealplanner.domain.physicalquantities.PhysicalQuantity
import com.darrenfinch.mymealplanner.model.data.entities.Food

interface SelectFoodQuantityViewMvc : ObservableViewMvc<SelectFoodQuantityViewMvc.Listener> {
    interface Listener {
        fun onFoodServingSizeChosen(selectedFood: Food, selectedFoodQuantity: PhysicalQuantity)
    }

    fun bindFood(food: Food)
    fun makeDialog() : Dialog

    // TODO: Remove when view models are introduced
    fun getFoodData(): Food
}
