package com.makzimi.finmarket.common.viewmodel

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearListDecorator(private val margin: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val count = parent.adapter?.itemCount ?: 0
        val halfMargin = margin / 2
        with(outRect) {
            left = margin
            right = margin
            top = if(position == 0) margin else halfMargin
            bottom = if(position == count - 1) margin else halfMargin
        }
    }
}