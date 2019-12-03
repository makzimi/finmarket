package com.makzimi.finmarket.catalog.stocklist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.makzimi.finmarket.catalog.stocklist.data.StockListRepository
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.model.StockEntity
import javax.inject.Inject

class StockListViewModel @Inject constructor(
    private val repository: StockListRepository
): ViewModel() {

    private var stocksFromDB: LiveData<PagedList<StockEntity>> = repository.observePagedStocks()
    private val _stocks: MediatorLiveData<RepositoryResult<PagedList<StockEntity>>> = MediatorLiveData()
    val stocks: LiveData<RepositoryResult<PagedList<StockEntity>>> = _stocks

    init {
        _stocks.value = RepositoryResult.loading()
        _stocks.addSource(stocksFromDB) {
            if(it.isNullOrEmpty()) {
                repository.updatePricesIfNeeded()
            } else {
                _stocks.value = RepositoryResult.success(it)
                repository.updatePricesIfNeeded()
            }
        }
    }

    fun fetchStocks() {
        repository.updatePricesIfNeeded(true)
    }

    override fun onCleared() {
        super.onCleared()
        _stocks.removeSource(stocksFromDB)
    }

}