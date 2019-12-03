package com.makzimi.finmarket.news.data

import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.common.repository.BaseDataSource
import com.makzimi.finmarket.model.NewsEntity
import com.makzimi.finmarket.network.news.NewsApiResponse
import com.makzimi.finmarket.network.news.NewsService
import io.reactivex.Single
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val api: NewsService) : BaseDataSource() {

    fun getNewsAbout(symbol: String): Single<RepositoryResult<List<NewsEntity>>> {
        return getResult(api.getNews(symbol),
            NewsApiResponse.Companion::emptyResult,
            NewsApiResponse.Companion::converter
        ).map { repositoryResult ->
            if(repositoryResult.status == RepositoryResult.Status.SUCCESS) {
                repositoryResult.data?.let { list ->
                    for (newsEntity in list) {
                        newsEntity.symbol = symbol
                    }
                }
            }
            repositoryResult
        }
    }
}