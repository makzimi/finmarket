package com.makzimi.finmarket.di.favorites

import androidx.lifecycle.ViewModel
import com.makzimi.finmarket.di.ViewModelKey
import com.makzimi.finmarket.favorites.ui.FavoritesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract
class FavoritesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindsStockListViewModel(favoritesViewModel: FavoritesViewModel) : ViewModel

}