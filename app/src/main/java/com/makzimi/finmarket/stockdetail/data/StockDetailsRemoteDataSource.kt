package com.makzimi.finmarket.stockdetail.data

import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.common.repository.BaseDataSource
import com.makzimi.finmarket.model.StockEntity
import com.makzimi.finmarket.network.stocks.StocksService
import com.makzimi.finmarket.network.stocks.entity.CompanyStockApiEntity
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockDetailsRemoteDataSource @Inject constructor(private val api: StocksService) : BaseDataSource() {

    fun getCompanyInfo(symbol: String): Single<RepositoryResult<StockEntity>> {
        return getResult(api.getCompanyInfo(symbol),
            CompanyStockApiEntity.Companion::emptyResult,
            CompanyStockApiEntity.Companion::converter
        )
    }

}