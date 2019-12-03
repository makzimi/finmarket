package com.makzimi.finmarket.di.stocklist

import androidx.lifecycle.ViewModel
import com.makzimi.finmarket.catalog.stocklist.ui.StockListViewModel
import com.makzimi.finmarket.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StockListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StockListViewModel::class)
    abstract fun bindsStockListViewModel(stockListViewModel: StockListViewModel) : ViewModel
}