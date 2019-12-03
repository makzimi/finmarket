package com.makzimi.finmarket.network.news.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NewsSourceApiEntity (

    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("name")
    @Expose
    var name: String

)