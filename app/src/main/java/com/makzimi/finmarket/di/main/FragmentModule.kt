package com.makzimi.finmarket.di.main

import android.content.Context
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makzimi.finmarket.R
import com.makzimi.finmarket.common.viewmodel.GridItemDecoration
import com.makzimi.finmarket.common.viewmodel.LinearListDecorator
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FragmentModule {

    @Provides
    fun provideListDecorator(context: Context, @Named("stocksColumnCount") stocksColumnCount: Int): RecyclerView.ItemDecoration {
        return  if(stocksColumnCount == 1) getListDecorator(context)
                else getGridDecorator(context, stocksColumnCount)
    }

    private fun getListDecorator(context: Context): RecyclerView.ItemDecoration {
        return LinearListDecorator(context.resources.getDimension(R.dimen.margin_list).toInt())
    }

    private fun getGridDecorator(context: Context, spanCount: Int): RecyclerView.ItemDecoration {
        return GridItemDecoration(context.resources.getDimension(R.dimen.margin_list).toInt(), spanCount)
    }

    @Provides
    fun provideLayoutManager(context: Context, @Named("stocksColumnCount") stocksColumnCount: Int): RecyclerView.LayoutManager {
        return  if(stocksColumnCount == 1) getLinearLayoutManager(context)
                else getGridLayoutManager(context, stocksColumnCount)
    }

    private fun getLinearLayoutManager(context: Context): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

    private fun getGridLayoutManager(context: Context, spanCount: Int): GridLayoutManager {
        return GridLayoutManager(context, spanCount)
    }

    @Provides
    fun provideNavigationOptions(): NavOptions {
        return navOptions {
            anim {
                enter = R.anim.slide_in_top
                exit = R.anim.slide_out_top
                popEnter = R.anim.slide_in_bottom
                popExit = R.anim.slide_out_bottom
            }
        }
    }
}