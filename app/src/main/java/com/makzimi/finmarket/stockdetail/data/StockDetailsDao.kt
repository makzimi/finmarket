package com.makzimi.finmarket.stockdetail.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makzimi.finmarket.model.StockEntity

@Dao
interface StockDetailsDao {
    @Query("SELECT * FROM stock WHERE symbol = :symbol")
    fun getStockDetails(symbol: String): LiveData<StockEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stock: StockEntity)
}