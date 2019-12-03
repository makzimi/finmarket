package com.makzimi.finmarket.di

import androidx.lifecycle.ViewModelProvider
import com.makzimi.finmarket.common.viewmodel.CustomViewModelProvidersFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelProvidersFactory(customViewModelFactory: CustomViewModelProvidersFactory)
            : ViewModelProvider.Factory
}