package com.makzimi.finmarket.di

import com.makzimi.finmarket.di.main.MainActivityModule
import com.makzimi.finmarket.di.main.MainFragmentBuildersModule
import com.makzimi.finmarket.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
        modules = [MainActivityModule::class,
            MainFragmentBuildersModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}