package com.makzimi.finmarket.common.viewmodel

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridItemDecoration(private val spacing: Int,
                         private val spanCount: Int
                         ) : RecyclerView.ItemDecoration()
{
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val count = parent.adapter?.itemCount?:0
        val column = position % spanCount
        val row = position / spanCount

        with(outRect) {
            left = if(column == 0) spacing else spacing / 2
            right = if(column == spanCount - 1) spacing else spacing / 2
            top = if(row == 0) spacing else spacing / 2
            bottom = if(row == count - 1) spacing else spacing / 2
        }
    }
}