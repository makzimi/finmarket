package com.makzimi.finmarket.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity (

    @PrimaryKey
    @ColumnInfo(name = "symbol")
    val symbol: String

)