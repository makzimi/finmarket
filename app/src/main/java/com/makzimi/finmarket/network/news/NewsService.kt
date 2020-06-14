package com.makzimi.finmarket.network.news

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    companion object {
        const val ENDPOINT = "https://newsapi.org/"
        const val API_KEY_NAME = "apiKey"
    }

    @GET("/v2/everything?language=en")
    fun getNews(@Query("q") q: String) : Single<NewsApiResponse>

}