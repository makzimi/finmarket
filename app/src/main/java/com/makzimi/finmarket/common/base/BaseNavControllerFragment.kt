package com.makzimi.finmarket.common.base

import androidx.navigation.NavController

abstract class BaseNavControllerFragment: BaseFragment() {

    abstract fun getNavController(): NavController

    override fun onBackPressed(): Boolean {
        return getNavController().navigateUp()
    }
}