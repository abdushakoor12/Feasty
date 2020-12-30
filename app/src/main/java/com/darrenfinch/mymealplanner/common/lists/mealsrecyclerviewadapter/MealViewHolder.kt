package com.darrenfinch.mymealplanner.common.lists.mealsrecyclerviewadapter

import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.databinding.ObservableBoolean
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darrenfinch.mymealplanner.R
import com.darrenfinch.mymealplanner.common.lists.BaseViewHolder
import com.darrenfinch.mymealplanner.common.lists.mealfoodsrecyclerviewadapter.MealFoodsRecyclerViewAdapter
import com.darrenfinch.mymealplanner.common.utils.AnimationUtils
import com.darrenfinch.mymealplanner.databinding.MealItemBinding
import com.darrenfinch.mymealplanner.model.data.entities.Meal

class MealViewHolder(private val config: MealsRecyclerViewAdapter.Config, private val listener: Listener, itemView: View) :
    BaseViewHolder<Meal>(itemView) {
    interface Listener {
        fun onSelect(meal: Meal)
        fun onEdit(mealId: Int)
        fun onDelete(meal: Meal)
    }

    private var binding = MealItemBinding.bind(itemView)
    var expanded = ObservableBoolean(false)
    override fun bind(item: Meal) {
        binding.meal = item
        binding.viewHolder = this
        binding.cardTop.setOnClickListener {
            listener.onSelect(item)
        }
        if(config.showViewMoreButton) {
            binding.viewMoreButton.setOnClickListener {
                PopupMenu(itemView.context, binding.dropdownImageButton, Gravity.BOTTOM).apply {
                    inflate(R.menu.context_menu)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.edit -> listener.onEdit(item.id)
                            R.id.delete -> listener.onDelete(item)
                        }
                        true
                    }
                    show()
                }
            }
        }
        else {
            binding.viewMoreButton.visibility = View.GONE
        }
        initAdapter(item)
    }

//    private fun displayContextMenu() {
//        PopupMenu(itemView.context, binding.dropdownImageButton, Gravity.BOTTOM).apply {
//            inflate(R.menu.context_menu)
//            setOnMenuItemClickListener { menuItem ->
//                handleMenuItemClicked(menuItem)
//            }
//            show()
//        }
//    }

//    private fun handleMenuItemClicked(menuItem: MenuItem): Boolean {
//        when (menuItem.itemId) {
//            R.id.edit -> listener.onEdit(binding.meal!!.id)
//            R.id.delete -> listener.onDelete(binding.meal!!)
//        }
//        return true
//    }

    fun inverseExpanded() {
        if (expanded.get()) AnimationUtils.collapse(binding.cardBottom) else AnimationUtils.expand(
            binding.cardBottom
        )
        expanded.set(!expanded.get())
    }

    private fun initAdapter(meal: Meal) {
        val adapter = MealFoodsRecyclerViewAdapter()
        binding.mealFoodsRecyclerView.adapter = adapter
        binding.mealFoodsRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
        adapter.updateItems(meal.foods.toMutableList())
    }
}