package com.makzimi.finmarket.di.main

import androidx.fragment.app.FragmentPagerAdapter
import com.makzimi.finmarket.main.MainActivity
import com.makzimi.finmarket.main.MainViewPagerAdapter
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun providesListDecorator(mainActivity: MainActivity): MainViewPagerAdapter {
        return MainViewPagerAdapter(
            mainActivity.supportFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
    }

}