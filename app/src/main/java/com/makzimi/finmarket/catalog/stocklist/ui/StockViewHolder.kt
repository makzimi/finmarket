package com.makzimi.finmarket.catalog.stocklist.ui

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makzimi.finmarket.databinding.ItemStockBinding
import com.makzimi.finmarket.model.StockEntity

class StockViewHolder(
    binding: ItemStockBinding,
    private val onCategoryClick: ((StockEntity) -> Unit)?)
    : RecyclerView.ViewHolder(binding.root) {

    private val uiSymbol: TextView = binding.uiSymbol
    private val uiPrice: TextView = binding.uiPrice

    fun bind(entity: StockEntity) {
        uiSymbol.text = entity.symbol
        uiPrice.text = entity.price.toString()

        onCategoryClick?.run {
            itemView.setOnClickListener {
                this.invoke(entity)
            }
        } ?: itemView.setOnClickListener(null)
    }
}