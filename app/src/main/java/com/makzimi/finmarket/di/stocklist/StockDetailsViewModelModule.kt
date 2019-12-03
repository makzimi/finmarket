package com.makzimi.finmarket.di.stocklist

import androidx.lifecycle.ViewModel
import com.makzimi.finmarket.di.ViewModelKey
import com.makzimi.finmarket.stockdetail.ui.StockDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StockDetailsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StockDetailsViewModel::class)
    abstract fun bindsStockListViewModel(stockDetailsViewModel: StockDetailsViewModel) : ViewModel
}