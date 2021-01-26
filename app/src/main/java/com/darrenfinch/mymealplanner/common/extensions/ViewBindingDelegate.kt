package com.darrenfinch.mymealplanner.common.extensions

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

class ViewBindingDelegate<T : ViewBinding> private constructor(
    private val fragment: Fragment,
    private val bind: (View) -> T
) {
    companion object Factory {
        fun <T : ViewBinding> Fragment.viewBinding(bind: (View) -> T): ViewBindingDelegate<T> =
            ViewBindingDelegate(this, bind)
    }
    private var binding: T? = fragment.view?.let { bind(it) }
    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment, { owner ->
            binding = owner?.let { bind(fragment.requireView()) }
        })
    }
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = binding
        ?: error("Cannot access view binding before onCreateView() or after onDestroyView()")
}