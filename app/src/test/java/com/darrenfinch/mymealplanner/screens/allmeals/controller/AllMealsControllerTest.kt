package com.darrenfinch.mymealplanner.screens.allmeals.controller

import androidx.lifecycle.*
import com.darrenfinch.mymealplanner.InstantExecutorExtension
import com.darrenfinch.mymealplanner.TestData
import com.darrenfinch.mymealplanner.common.misc.ScreensNavigator
import com.darrenfinch.mymealplanner.screens.allmeals.view.AllMealsViewMvc
import com.darrenfinch.mymealplanner.meals.usecases.DeleteMealUseCase
import com.darrenfinch.mymealplanner.meals.usecases.GetAllMealsUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class AllMealsControllerTest {

    private val defaultMealListLiveData = TestData.defaultMealListLiveData
    private val defaultMealListData = TestData.defaultMealListData

    private val viewLifecycleOwner = mockk<LifecycleOwner>()
    private val lifecycle = LifecycleRegistry(viewLifecycleOwner)

    private val screensNavigator = mockk<ScreensNavigator>(relaxUnitFun = true)
    private val getAllMealsUseCase = mockk<GetAllMealsUseCase>(relaxUnitFun = true)
    private val deleteMealUseCase = mockk<DeleteMealUseCase>(relaxUnitFun = true)

    private val viewMvc = mockk<AllMealsViewMvc>(relaxUnitFun = true)

    private lateinit var SUT: AllMealsController

    @BeforeEach
    internal fun setUp() {
        SUT = AllMealsController(screensNavigator, getAllMealsUseCase, deleteMealUseCase)
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
        every { getAllMealsUseCase.fetchAllMealsAndNotify() } returns defaultMealListLiveData
        SUT.getAllMealsAndBindToView(viewLifecycleOwner)
        verify { viewMvc.bindMeals(defaultMealListData) }
        verify { getAllMealsUseCase.fetchAllMealsAndNotify() }
    }

    private fun setupInstantLifecycleEventComponents() {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
        every { viewLifecycleOwner.lifecycle } returns lifecycle
    }
}