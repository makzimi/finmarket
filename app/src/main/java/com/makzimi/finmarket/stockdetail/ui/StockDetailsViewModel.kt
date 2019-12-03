package com.makzimi.finmarket.stockdetail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.model.NewsEntity
import com.makzimi.finmarket.model.StockEntity
import com.makzimi.finmarket.stockdetail.data.StockDetailsRepository
import javax.inject.Inject

class StockDetailsViewModel @Inject constructor(
    private val repository: StockDetailsRepository
): ViewModel() {

    var symbol: String = ""
    private lateinit var companyInfoFromDB: LiveData<StockEntity>
    private lateinit var isFavoritesFromDB: LiveData<Int>
    private lateinit var newsFromDB: LiveData<List<NewsEntity>>

    private val _companyInfo: MediatorLiveData<RepositoryResult<StockEntity>> = MediatorLiveData()
    val companyInfo: LiveData<RepositoryResult<StockEntity>> = _companyInfo

    private val _isFavorites: MediatorLiveData<Boolean> = MediatorLiveData()
    val isFavorites: LiveData<Boolean> = _isFavorites

    private val _news: MediatorLiveData<RepositoryResult<List<NewsEntity>>> = MediatorLiveData()
    val news: LiveData<RepositoryResult<List<NewsEntity>>> = _news

    fun observeStockDetails() {
        companyInfoFromDB = repository.observeCompanyDetails(symbol)

        _companyInfo.value = RepositoryResult.loading()
        _companyInfo.addSource(companyInfoFromDB) {
            if(!it?.companyName.isNullOrEmpty()) {
                _companyInfo.value = RepositoryResult.success(it)
            }
        }

        isFavoritesFromDB = repository.observeFavorites(symbol)
        _isFavorites.addSource(isFavoritesFromDB) {
            _isFavorites.value = it == 1
        }

        _news.value = RepositoryResult.loading()
        newsFromDB = repository.observeNews(symbol)
        _news.addSource(newsFromDB) {
            if(it.isNullOrEmpty()) {
                repository.fetchNews(symbol)
            } else {
                _news.value = RepositoryResult.success(it)
            }
        }

        // fetching data anyway
        repository.fetchDetails(symbol)
    }

    fun addToFavourites(stockEntity: StockEntity, todoAdd: Boolean) {
        repository.addToFavorites(stockEntity.symbol, todoAdd)
    }

    override fun onCleared() {
        super.onCleared()
        _companyInfo.removeSource(companyInfoFromDB)
        _isFavorites.removeSource(isFavoritesFromDB)
        _news.removeSource(newsFromDB)
    }

}