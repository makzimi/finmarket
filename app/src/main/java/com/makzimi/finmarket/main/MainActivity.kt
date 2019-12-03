package com.makzimi.finmarket.main

import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.makzimi.finmarket.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(),
    ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener
{

    companion object {
        const val CATALOG_PAGE_INDEX = 0
        const val FAVORITES_PAGE_INDEX = 1
        const val SETTINGS_PAGE_INDEX = 2
        const val DEFAULT_PAGE_INDEX =
            CATALOG_PAGE_INDEX
    }

    private val viewPagerBackStack = Stack<Int>()

    private val pageMenuMap = mapOf(
        CATALOG_PAGE_INDEX to R.id.catalog,
        FAVORITES_PAGE_INDEX to R.id.favorites,
        SETTINGS_PAGE_INDEX to R.id.settings)

    @Inject
    lateinit var mainViewPagerAdapter: MainViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        uiBottomNavigation.setOnNavigationItemSelectedListener(this)

        uiViewPager.adapter = mainViewPagerAdapter
        uiViewPager.currentItem = DEFAULT_PAGE_INDEX

        if(viewPagerBackStack.size == 0) {
            viewPagerBackStack.push(DEFAULT_PAGE_INDEX)
        }
    }

    override fun onStart() {
        super.onStart()

        uiViewPager.addOnPageChangeListener(this)
    }

    override fun onStop() {
        super.onStop()

        uiViewPager.removeOnPageChangeListener(this)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        val newId = pageMenuMap[position] ?: DEFAULT_PAGE_INDEX
        if(uiBottomNavigation.selectedItemId != newId) {
            uiBottomNavigation.selectedItemId = newId
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val newPosition = pageMenuMap.values.indexOf(menuItem.itemId)

        if(uiViewPager.currentItem != newPosition) {
            uiViewPager.currentItem = newPosition
            viewPagerBackStack.push(newPosition)
        }

        return true
    }

    override fun onBackPressed() {
        val currentFragment = mainViewPagerAdapter.getFragmentByPosition(uiViewPager.currentItem)
        val navigatedUp = currentFragment?.onBackPressed() ?: false

        if(!navigatedUp) {
            if(viewPagerBackStack.size > 1) {
                viewPagerBackStack.pop()
                uiViewPager.currentItem = viewPagerBackStack.peek()
            } else {
                super.onBackPressed()
            }
        }
    }
}