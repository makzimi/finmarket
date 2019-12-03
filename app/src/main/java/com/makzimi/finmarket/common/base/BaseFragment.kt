package com.makzimi.finmarket.common.base

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    open fun onBackPressed(): Boolean {
        return false
    }
}