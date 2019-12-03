package com.makzimi.finmarket.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.favorites.data.FavoritesRepository
import com.makzimi.finmarket.model.StockEntity
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val repository: FavoritesRepository
): ViewModel() {

    private var favoritesSymbols: LiveData<List<StockEntity>> = repository.observeFavorites()
    private val _stocks: MediatorLiveData<RepositoryResult<List<StockEntity>>> = MediatorLiveData()
    val stocks: LiveData<RepositoryResult<List<StockEntity>>> = _stocks

    var lastResult: List<StockEntity> = listOf()

    init {
        _stocks.value = RepositoryResult.loading()
        _stocks.addSource(favoritesSymbols) {
            if(it.isNullOrEmpty()) {
                _stocks.value = RepositoryResult.success(listOf())
            } else {
                _stocks.value = RepositoryResult.success(it)
                lastResult = it
                repository.updatePricesIfNeeded(it)
            }
        }
    }

    fun fetchFavorites(): Boolean {
        return if(!lastResult.isNullOrEmpty()) {
            repository.updatePricesIfNeeded(lastResult, true)
            true
        } else {
            false
        }
    }

    override fun onCleared() {
        super.onCleared()
        _stocks.removeSource(favoritesSymbols)
    }

}