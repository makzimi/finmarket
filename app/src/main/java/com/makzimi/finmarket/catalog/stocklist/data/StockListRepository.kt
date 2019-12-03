package com.makzimi.finmarket.catalog.stocklist.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.model.StockEntity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockListRepository @Inject constructor(
    private val dao: StockListDao,
    private val remoteDataSource: StockListRemoteDataSource
){
    companion object {
        const val REFRESH_TIMEOUT = 60
        const val PAGE = 20
    }

    private var disposable = CompositeDisposable()
    private var lastFetchTime: Long = 0

    fun observePagedStocks(): LiveData<PagedList<StockEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE)
            .build()
        return LivePagedListBuilder<Int, StockEntity>(dao.getAllPaged(), config)
            .build()
    }

    private fun fetch() {
        disposable.add(
            remoteDataSource.getAllPrices().subscribe { repositoryResult ->
                    if(repositoryResult.status == RepositoryResult.Status.SUCCESS) {
                        repositoryResult.data?.let {
                            lastFetchTime = System.currentTimeMillis()
                            dao.updateAllStocks(it)
                        }
                    }
                    disposable.clear()
                })
    }

    fun updatePricesIfNeeded(force: Boolean = false) {
        if(force || (System.currentTimeMillis() - lastFetchTime) > REFRESH_TIMEOUT * 1000) {
            fetch()
        }
    }
}