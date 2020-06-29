package com.darrenfinch.mymealplanner.domain.allmeals.controller

import android.app.Application
import androidx.lifecycle.*
import com.darrenfinch.mymealplanner.common.di.AppModule
import com.darrenfinch.mymealplanner.common.di.DaggerApplicationComponent
import com.darrenfinch.mymealplanner.common.di.RoomModule
import com.darrenfinch.mymealplanner.model.MealsRepository
import com.darrenfinch.mymealplanner.model.data.Meal
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllMealsViewModel(application: Application) : AndroidViewModel(application)
{
    @Inject
    lateinit var repo: MealsRepository
    init
    {
        DaggerApplicationComponent.builder()
            .appModule(AppModule(application))
            .roomModule(RoomModule(application))
            .build()
            .inject(this)
    }

    private val allMealsLiveData: MutableLiveData<List<Meal>> = MutableLiveData()

    fun fetchAllMeals() : LiveData<List<Meal>>
    {
        viewModelScope.launch {
            allMealsLiveData.postValue(repo.getMeals())
        }
        return allMealsLiveData
    }
}