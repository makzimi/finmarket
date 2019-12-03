package com.makzimi.finmarket.favorites.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.makzimi.finmarket.model.FavoriteEntity
import com.makzimi.finmarket.model.StockEntity

@Dao
interface FavoritesDao {

    @Query("SELECT stock.symbol AS symbol, stock.price AS price FROM stock INNER JOIN favorite ON stock.symbol = favorite.symbol")
    fun getAll(): LiveData<List<StockEntity>>

    @Query("SELECT COUNT(symbol) FROM favorite WHERE symbol = :symbol")
    fun isFavorite(symbol: String): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stocks: List<FavoriteEntity>)

    @Delete
    fun deleteList(stocks: List<FavoriteEntity>)

    @Query("DELETE FROM favorite")
    fun deleteAll()
}