package com.makzimi.finmarket.network.news

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    companion object {
        const val ENDPOINT = "https://newsapi.org/"

        // You can retrieve your own api key on https://newsapi.org/
        const val API_KEY = "92d610963da14182bccb8fe180cc993d"
    }

    @GET("/v2/everything?language=en")
    fun getNews(@Query("q") q: String) : Single<NewsApiResponse>

}