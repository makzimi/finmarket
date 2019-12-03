package com.makzimi.finmarket.network.stocks.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CompanyProfileApiEntity (

    @SerializedName("price")
    @Expose
    var price: Double,

    @SerializedName("volAvg")
    @Expose
    val volAvg: String,

    @SerializedName("mktCap")
    @Expose
    val mktCap: Double,

    @SerializedName("lastDiv")
    @Expose
    val lastDiv: Double,

    @SerializedName("companyName")
    @Expose
    val companyName: String,

    @SerializedName("industry")
    @Expose
    val industry: String,

    @SerializedName("website")
    @Expose
    val website: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("ceo")
    @Expose
    val ceo: String,

    @SerializedName("sector")
    @Expose
    val sector: String,

    @SerializedName("image")
    @Expose
    val image: String

)