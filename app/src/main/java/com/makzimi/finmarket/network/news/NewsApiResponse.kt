package com.makzimi.finmarket.network.news

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.model.NewsEntity
import com.makzimi.finmarket.network.news.entity.NewsArticleApiEntity

class NewsApiResponse (

    @SerializedName("status")
    @Expose
    val status: String,

    @SerializedName("message")
    @Expose
    var message: String? = null,

    @SerializedName("totalResults")
    @Expose
    val totalResults: Int = 0,

    @SerializedName("articles")
    @Expose
    var articles: List<NewsArticleApiEntity>

) {

    companion object {

        const val STATUS_OK = "ok"
        const val STATUS_ERROR = "error"

        fun emptyResult(throwable: Throwable): NewsApiResponse {
            return NewsApiResponse(status = "error", message = throwable.message ?: "Unknown error", articles = listOf())
        }

        fun converter(newsApiResponse: NewsApiResponse): RepositoryResult<List<NewsEntity>> {
            return if(newsApiResponse.status == STATUS_OK) {
                RepositoryResult.success(newsApiResponse.articles.map {
                    NewsEntity(title = it.title,
                        description = it.description,
                        author = it.author,
                        source = it.source.name,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt,
                        content = it.content
                    )
                })
            } else {
                RepositoryResult.error(newsApiResponse.message)
            }
        }
    }
}