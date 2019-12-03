package com.makzimi.finmarket.catalog.stocklist.data

import androidx.paging.DataSource
import androidx.room.*
import com.makzimi.finmarket.model.StockEntity

@Dao
interface StockListDao {

    @Query("SELECT * FROM stock ORDER BY symbol")
    fun getAllPaged() : DataSource.Factory<Int, StockEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stocks: List<StockEntity>)

    @Query("DELETE FROM stock")
    fun deleteAll()

    @Transaction
    fun updateAllStocks(stocks: List<StockEntity>) {
        deleteAll()
        insert(stocks)
    }
}