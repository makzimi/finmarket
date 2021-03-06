package com.makzimi.finmarket.favorites.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.makzimi.finmarket.catalog.stocklist.ui.StockViewHolder
import com.makzimi.finmarket.databinding.ItemStockBinding
import com.makzimi.finmarket.model.StockEntity

class FavoritesListAdapter: ListAdapter<StockEntity, StockViewHolder>(DiffCallback()){

    var onCategoryClick: ((StockEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            ItemStockBinding.inflate(
                LayoutInflater.from(parent.context), parent, false),
            onCategoryClick)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stockEntity = getItem(position)
        stockEntity?.let {
            holder.bind(stockEntity)
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<StockEntity>() {

    override fun areItemsTheSame(oldItem: StockEntity, newItem: StockEntity): Boolean {
        return oldItem.symbol == newItem.symbol
    }

    override fun areContentsTheSame(oldItem: StockEntity, newItem: StockEntity): Boolean {
        return (oldItem.symbol == newItem.symbol &&
                oldItem.price == newItem.price)
    }
}