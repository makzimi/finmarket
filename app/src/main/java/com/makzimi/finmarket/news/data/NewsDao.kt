package com.makzimi.finmarket.news.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.makzimi.finmarket.model.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news_article WHERE symbol = :symbol")
    fun getNewsForSymbol(symbol: String): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: List<NewsEntity>)

    @Query("DELETE FROM news_article WHERE symbol = :symbol")
    fun deleteNewsForSymbol(symbol: String)

    @Transaction
    fun updateAllNewsForSymbol(symbol: String, news: List<NewsEntity>) {
        deleteNewsForSymbol(symbol)
        insert(news)
    }
}