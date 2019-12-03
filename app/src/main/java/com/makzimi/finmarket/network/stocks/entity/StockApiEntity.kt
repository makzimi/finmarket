package com.makzimi.finmarket.network.stocks.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StockApiEntity (

    @SerializedName("symbol")
    @Expose
    val symbol: String,

    @SerializedName("price")
    @Expose
    var price: Double

)