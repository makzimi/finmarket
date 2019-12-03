package com.makzimi.finmarket.di.main

import com.makzimi.finmarket.catalog.stocklist.ui.StockListFragment
import com.makzimi.finmarket.di.favorites.FavoritesViewModelModule
import com.makzimi.finmarket.di.stocklist.StockDetailsViewModelModule
import com.makzimi.finmarket.di.stocklist.StockListViewModelModule
import com.makzimi.finmarket.favorites.ui.FavoritesFragment
import com.makzimi.finmarket.stockdetail.ui.StockDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector(
        modules = [ FragmentModule::class,
                    StockListViewModelModule::class]
    )
    abstract fun contributeStockListFragment(): StockListFragment

    @ContributesAndroidInjector(
        modules = [ FragmentModule::class,
                    StockDetailsViewModelModule::class]
    )
    abstract fun contributeStockDetailsFragment(): StockDetailsFragment

    @ContributesAndroidInjector(
        modules = [ FragmentModule::class,
                    FavoritesViewModelModule::class]
    )
    abstract fun contributeFavoritesFragment(): FavoritesFragment

}