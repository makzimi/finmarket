package com.makzimi.finmarket.favorites.data

import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.common.repository.BaseDataSource
import com.makzimi.finmarket.model.StockEntity
import com.makzimi.finmarket.network.stocks.StocksService
import com.makzimi.finmarket.network.stocks.entity.PriceListApiEntity
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRemoteDataSource @Inject constructor(private val api: StocksService) : BaseDataSource() {

    fun getSpecificPrices(symbols: String): Single<RepositoryResult<List<StockEntity>>> {
        return getResult(api.getSpecificPrices(symbols),
            PriceListApiEntity.Companion::emptyResult,
            PriceListApiEntity.Companion::converter
        )
    }
}