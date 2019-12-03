package com.makzimi.finmarket.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.makzimi.finmarket.catalog.stocklist.data.StockListDao
import com.makzimi.finmarket.favorites.data.FavoritesDao
import com.makzimi.finmarket.model.FavoriteEntity
import com.makzimi.finmarket.model.NewsEntity
import com.makzimi.finmarket.model.StockEntity
import com.makzimi.finmarket.news.data.NewsDao
import com.makzimi.finmarket.stockdetail.data.StockDetailsDao

@Database (entities = [ StockEntity::class,
                        FavoriteEntity::class,
                        NewsEntity::class],
    version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun stockListDao(): StockListDao
    abstract fun stockDetailsDao(): StockDetailsDao
    abstract fun favoritesDao(): FavoritesDao
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            var tempInstance = INSTANCE
            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java,
                    "app_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}