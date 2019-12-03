package com.makzimi.finmarket.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.makzimi.finmarket.network.stocks.entity.CompanyStockApiEntity
import com.makzimi.finmarket.network.stocks.entity.StockApiEntity

@Entity(tableName = "stock")
data class StockEntity(

    @PrimaryKey
    @ColumnInfo(name = "symbol")
    val symbol: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "mkt_cap")
    val mktCap: Double? = null,

    @ColumnInfo(name = "last_div")
    val lastDiv: Double? = null,

    @ColumnInfo(name = "name")
    val companyName: String? = null,

    @ColumnInfo(name = "industry")
    val industry: String? = null,

    @ColumnInfo(name = "sector")
    val sector: String? = null,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "ceo")
    val ceo: String? = null,

    @ColumnInfo(name = "image")
    val image: String? = null,

    @ColumnInfo(name = "website")
    val website: String? = null
) {
    constructor(stockApiEntity: StockApiEntity)
            : this( symbol = stockApiEntity.symbol,
                    price = stockApiEntity.price)

    constructor(company: CompanyStockApiEntity)
            : this( symbol = company.symbol!!,
                    price = company.profile!!.price!!,
                    mktCap = company.profile.mktCap,
                    lastDiv = company.profile.lastDiv,
                    companyName = company.profile.companyName,
                    industry = company.profile.industry,
                    sector = company.profile.sector,
                    description = company.profile.description,
                    ceo = company.profile.ceo,
                    image = company.profile.image,
                    website = company.profile.website)
}
