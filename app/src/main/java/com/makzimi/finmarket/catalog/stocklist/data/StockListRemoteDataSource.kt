package com.makzimi.finmarket.catalog.stocklist.data

import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.common.repository.BaseDataSource
import com.makzimi.finmarket.model.StockEntity
import com.makzimi.finmarket.network.stocks.StocksService
import com.makzimi.finmarket.network.stocks.entity.PriceListApiEntity
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockListRemoteDataSource @Inject constructor(private val api: StocksService) : BaseDataSource()  {

    fun getAllPrices(): Single<RepositoryResult<List<StockEntity>>> {
        return getResult(api.getNasdaqPrices(),
            PriceListApiEntity.Companion::emptyResult,
            PriceListApiEntity.Companion::converter
        )
    }
}