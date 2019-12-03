package com.makzimi.finmarket.network.stocks.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.model.StockEntity

data class PriceListApiEntity(
    @SerializedName("companiesPriceList")
    @Expose
    val symbolsList: List<StockApiEntity>
) {
    companion object {
        fun emptyResult(throwable: Throwable): PriceListApiEntity {
            return PriceListApiEntity(listOf())
        }

        fun converter(priceListApiEntity: PriceListApiEntity): RepositoryResult<List<StockEntity>> {
            return if(priceListApiEntity.symbolsList.isNullOrEmpty()) {
                RepositoryResult.error("Remote data is null")
            } else {
                RepositoryResult.success(priceListApiEntity.symbolsList.map { stockApiEntity ->
                    StockEntity(stockApiEntity)
                })
            }
        }
    }
}