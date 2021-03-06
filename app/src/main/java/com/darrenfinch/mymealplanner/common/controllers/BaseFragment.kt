package com.darrenfinch.mymealplanner.common.controllers

import androidx.fragment.app.Fragment
import com.darrenfinch.mymealplanner.common.dependencyinjection.ControllerCompositionRoot
import com.darrenfinch.mymealplanner.common.MainActivity

abstract class BaseFragment : Fragment() {
    protected val controllerCompositionRoot by lazy {
        ControllerCompositionRoot(
            (requireActivity() as MainActivity).activityCompositionRoot
        )
    }
}