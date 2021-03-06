package com.darrenfinch.mymealplanner.screens.allfoods.controller

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.darrenfinch.mymealplanner.InstantExecutorExtension
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.TestData.DEFAULT_VALID_FOOD_ID
import com.darrenfinch.mymealplanner.common.misc.ScreensNavigator
import com.darrenfinch.mymealplanner.screens.allfoods.view.AllFoodsViewMvc
import com.darrenfinch.mymealplanner.foods.usecases.DeleteFoodUseCase
import com.darrenfinch.mymealplanner.foods.usecases.GetAllFoodsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class AllFoodsControllerTest {

    private val defaultFoodListLiveData = TestData.defaultFoodListLiveData
    private val defaultFoodDataList = TestData.defaultFoodDataList

    private val viewLifecycleOwner = mockk<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(viewLifecycleOwner)

    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getAllFoodsUseCase = mockk<GetAllFoodsUseCase>(relaxUnitFun = true)
    private val deleteFoodUseCase = mockk<DeleteFoodUseCase>(relaxUnitFun = true)

    private val viewMvc = mockk<AllFoodsViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: AllFoodsController

    @BeforeEach
    internal fun setUp() {
        SUT = AllFoodsController(screensNavigator, getAllFoodsUseCase, deleteFoodUseCase)
        SUT.bindView(viewMvc)

        setupInstantLifecycleEventComponents()
    }

    @Test
    internal fun `onStart() subscribes to viewMvc`() {
        SUT.onStart()
        verify { viewMvc.registerListener(SUT) }
    }

    @Test
    internal fun `onStop() un-subscribes to viewMvc`() {
        SUT.onStop()
        verify { viewMvc.unregisterListener(SUT) }
    }

    @Test
    internal fun `fetchFoods() binds foods to viewMvc from use case`() {
        every { getAllFoodsUseCase.getAllFoods() } returns defaultFoodListLiveData
        SUT.getAllFoodsAndBindToView(viewLifecycleOwner)
        verify { viewMvc.bindFoods(defaultFoodDataList) }
        verify { getAllFoodsUseCase.getAllFoods() }
    }

    @Test
    internal fun `onItemEdit() navigates to add or edit food screen and passes correct foodId`() {
        SUT.onItemEdit(DEFAULT_VALID_FOOD_ID)
        verify { screensNavigator.navigateFromAllFoodsScreenToFoodFormScreen(DEFAULT_VALID_FOOD_ID) }
    }

    @Test
    internal fun `onItemDelete() deletes food with use case`() {
        SUT.onItemDelete(DEFAULT_VALID_FOOD_ID)
        verify { deleteFoodUseCase.deleteFood(DEFAULT_VALID_FOOD_ID) }
    }

    private fun setupInstantLifecycleEventComponents() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        every { viewLifecycleOwner.lifecycle } returns lifecycle
    }
}