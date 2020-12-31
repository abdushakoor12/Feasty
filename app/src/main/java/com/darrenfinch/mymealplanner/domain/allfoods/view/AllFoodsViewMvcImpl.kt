package com.darrenfinch.mymealplanner.domain.allfoods.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.foodrecyclerviewadapter.FoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.lists.recyclerviewitemdecorations.MarginItemDecoration
import com.darrenfinch.mymealplanner.common.misc.KeyboardUtils
import com.darrenfinch.mymealplanner.common.views.BaseObservableViewMvc
import com.darrenfinch.mymealplanner.databinding.FragmentAllFoodsBinding
import com.darrenfinch.mymealplanner.model.data.entities.Food

class AllFoodsViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) : BaseObservableViewMvc<AllFoodsViewMvc.Listener>(), AllFoodsViewMvc {

    private val foodsListItemEventListener: FoodsRecyclerViewAdapter.ItemEventListener = object : FoodsRecyclerViewAdapter.ItemEventListener {
        override fun onItemClick(foodId: Int) { }
        override fun onItemClick(food: Food) { }
        override fun onItemEdit(foodId: Int) {
            notifyListenersOfItemEdit(foodId)
        }
        override fun onItemDelete(foodId: Int) {
            notifyListenersOfItemDelete(foodId)
        }
    }

    private fun notifyListenersOfItemDelete(foodId: Int) {
        for(listener in getListeners())
            listener.onItemDelete(foodId)
    }

    private fun notifyListenersOfItemEdit(foodId: Int) {
        for(listener in getListeners()) {
            listener.onItemEdit(foodId)
        }
    }

    private val foodsListAdapter = FoodsRecyclerViewAdapter(FoodsRecyclerViewAdapter.Config(true), foodsListItemEventListener)

    private val binding: FragmentAllFoodsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_foods, parent, false)

    init {
        setRootView(binding.root)
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            foodsRecyclerView.adapter = foodsListAdapter
            foodsRecyclerView.layoutManager = LinearLayoutManager(getContext())
            foodsRecyclerView.addItemDecoration(MarginItemDecoration(16))

            toolbar.inflateMenu(R.menu.add_new_item_menu)
            toolbar.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.addNewItem -> onAddNewFoodClicked()
                }
                true
            }
        }
    }

    private fun onAddNewFoodClicked() {
        for(listener in getListeners()) {
            listener.addNewFoodClicked()
        }
    }

    override fun bindFoods(newFoods: List<Food>) {
        foodsListAdapter.updateItems(newFoods)
    }
}