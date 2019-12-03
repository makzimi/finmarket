package com.makzimi.finmarket.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.makzimi.finmarket.appinfo.AppInfoFragment
import com.makzimi.finmarket.catalog.CatalogHomeFragment
import com.makzimi.finmarket.common.base.BaseFragment
import com.makzimi.finmarket.favorites.FavoritesHomeFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fragmentManager, behavior) {

    private var fragmentsFunctions: List<() -> BaseFragment> = listOf(
        { CatalogHomeFragment.newInstance()},
        { FavoritesHomeFragment.newInstance()},
        { AppInfoFragment.newInstance()}
    )

    private var fragments: MutableList<BaseFragment> = mutableListOf()

    override fun getCount(): Int {
        return fragmentsFunctions.size
    }

    override fun getItem(position: Int): Fragment {
        val f = fragmentsFunctions[position]()
        fragments.add(position, f)
        return f
    }

    fun getFragmentByPosition(position: Int): BaseFragment? {
        return if(fragments.size > 0 && position >= 0 && position < fragments.size) {
            fragments[position]
        } else {
            null
        }
    }

}