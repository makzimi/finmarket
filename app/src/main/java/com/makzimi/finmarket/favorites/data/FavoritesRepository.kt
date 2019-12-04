package com.makzimi.finmarket.favorites.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.makzimi.finmarket.catalog.stocklist.data.StockListDao
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.model.StockEntity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository  @Inject constructor(
    private val favoritesDao: FavoritesDao,
    private val stockListDao: StockListDao,
    private val remoteDataSource: FavoritesRemoteDataSource
){
    companion object {
        const val REFRESH_TIMEOUT = 60
    }

    private val allFavoritesStream = favoritesDao.getFavorites()
    private val _favoritesStocks: MediatorLiveData<List<StockEntity>> = MediatorLiveData()
    private val favoritesStocks: LiveData<List<StockEntity>> = _favoritesStocks

    private var disposable = CompositeDisposable()
    private var lastFetchTime: Long = 0

    init {
        val source: LiveData<List<StockEntity>> =
            LiveDataReactiveStreams.fromPublisher(allFavoritesStream.map{
                    val fullFavoritesStockList =
                        if(it.isNullOrEmpty()) {
                            listOf()
                        } else {
                            favoritesDao.getFavoritesFull()
                        }
                    fullFavoritesStockList
                }
            )

        _favoritesStocks.addSource(source) {
            _favoritesStocks.value = it
        }
    }

    fun observeFavorites(): LiveData<List<StockEntity>> {
        return favoritesStocks
    }

    private fun fetch(favoriteStocks: List<StockEntity>) {
        disposable.add(
            remoteDataSource.getSpecificPrices(favoriteStocks.joinToString(",") { it.symbol })
                .subscribe { repositoryResult ->
                    if(repositoryResult.status == RepositoryResult.Status.SUCCESS) {
                        repositoryResult.data?.let {
                            lastFetchTime = System.currentTimeMillis()
                            stockListDao.insert(it)
                        }
                    }
                    disposable.clear()
                })
    }

    fun updatePricesIfNeeded(favoriteStocks: List<StockEntity>, force: Boolean = false) {
        if(force || (System.currentTimeMillis() - lastFetchTime) > REFRESH_TIMEOUT * 1000) {
            fetch(favoriteStocks)
        }
    }
}