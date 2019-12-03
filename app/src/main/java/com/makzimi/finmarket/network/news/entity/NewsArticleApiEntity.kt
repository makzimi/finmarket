package com.makzimi.finmarket.network.news.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsArticleApiEntity (

    @SerializedName("author")
    @Expose
    val author: String,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("description")
    @Expose
    var description: String?,

    @SerializedName("url")
    @Expose
    var url: String?,

    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String?,

    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String?,

    @SerializedName("content")
    @Expose
    var content: String?,

    @SerializedName("source")
    @Expose
    var source: NewsSourceApiEntity

)