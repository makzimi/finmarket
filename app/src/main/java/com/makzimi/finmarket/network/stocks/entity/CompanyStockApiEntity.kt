package com.makzimi.finmarket.network.stocks.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.model.StockEntity

data class CompanyStockApiEntity (

    @SerializedName("symbol")
    @Expose
    val symbol: String?,

    @SerializedName("profile")
    @Expose
    val profile: CompanyProfileApiEntity?

) {
    companion object {
        fun emptyResult(throwable: Throwable): CompanyStockApiEntity {
            return CompanyStockApiEntity(null, null)
        }

        fun converter(companyStockApiEntity: CompanyStockApiEntity): RepositoryResult<StockEntity> {
            return if(companyStockApiEntity.symbol == null) {
                RepositoryResult.error("Remote data is null")
            } else {
                RepositoryResult.success(StockEntity(companyStockApiEntity))
            }
        }
    }
}