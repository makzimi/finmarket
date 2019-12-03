package com.makzimi.finmarket.stockdetail.data

import androidx.lifecycle.LiveData
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.favorites.data.FavoritesDao
import com.makzimi.finmarket.model.FavoriteEntity
import com.makzimi.finmarket.model.NewsEntity
import com.makzimi.finmarket.model.StockEntity
import com.makzimi.finmarket.news.data.NewsDao
import com.makzimi.finmarket.news.data.NewsRemoteDataSource
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockDetailsRepository @Inject constructor(
    private val stocksDetailsDao: StockDetailsDao,
    private val favoritesDao: FavoritesDao,
    private val stockDetailsRemoteDataSource: StockDetailsRemoteDataSource,
    private val newsDao: NewsDao,
    private val newsRemoteDataSource: NewsRemoteDataSource
){
    companion object {
        const val NEWS_REFRESH_TIMEOUT = 60 * 60 // articles update time on server
    }

    private var lastFetchTimeNews: Long = 0

    fun observeCompanyDetails(symbol: String): LiveData<StockEntity> {
        return stocksDetailsDao.getStockDetails(symbol)
    }

    fun observeFavorites(symbol: String): LiveData<Int> {
        return favoritesDao.isFavorite(symbol)
    }

    fun addToFavorites(symbol: String, todoAdd: Boolean) {
        val disposable = CompositeDisposable()
        disposable.add(
        Observable.just(favoritesDao)
            .subscribeOn(Schedulers.io())
            .subscribe{ dao ->
                if(todoAdd) {
                    dao.insert(listOf(FavoriteEntity(symbol)))
                } else {
                    dao.deleteList(listOf(FavoriteEntity(symbol)))
                }
                disposable.clear()
            })
    }

    fun fetchDetails(symbol: String) {
        val disposable = CompositeDisposable()
        disposable.add(
            stockDetailsRemoteDataSource.getCompanyInfo(symbol)
                .subscribe { repositoryResult ->
                    if(repositoryResult.status == RepositoryResult.Status.SUCCESS) {
                        repositoryResult.data?.let {
                            stocksDetailsDao.insert(it)
                        }
                    }
                    disposable.clear()
                })
    }

    fun observeNews(symbol: String): LiveData<List<NewsEntity>> {
        return newsDao.getNewsForSymbol(symbol)
    }

    fun fetchNews(symbol: String) {
        val disposable = CompositeDisposable()
        disposable.add(
            newsRemoteDataSource.getNewsAbout(symbol)
                .subscribe { repositoryResult ->
                    if(repositoryResult.status == RepositoryResult.Status.SUCCESS) {
                        repositoryResult.data?.let {
                            lastFetchTimeNews = System.currentTimeMillis()
                            newsDao.updateAllNewsForSymbol(symbol, it.subList(0, 3))
                        }
                    }
                    disposable.clear()
                })
    }
}