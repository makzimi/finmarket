package com.makzimi.finmarket.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

import com.makzimi.finmarket.R
import com.makzimi.finmarket.common.base.BaseNavControllerFragment
import kotlinx.android.synthetic.main.fragment_catalog_home.*

class CatalogHomeFragment  : BaseNavControllerFragment() {

    companion object {
        fun newInstance(): CatalogHomeFragment {
            return CatalogHomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_catalog_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.stockListDestination
        ))

        (activity as AppCompatActivity).setSupportActionBar(uiToolbar)

        NavigationUI.setupWithNavController(uiToolbar, getNavController(), appBarConfiguration)
    }

    override fun getNavController(): NavController {
        return Navigation.findNavController(requireActivity(), R.id.catalog_host)
    }
}
