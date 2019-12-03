package com.makzimi.finmarket.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

import com.makzimi.finmarket.R
import com.makzimi.finmarket.common.base.BaseNavControllerFragment
import kotlinx.android.synthetic.main.fragment_favorites_home.*

class FavoritesHomeFragment : BaseNavControllerFragment() {

    companion object {
        fun newInstance() : FavoritesHomeFragment {
            return FavoritesHomeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.favoritesDestination))

        NavigationUI.setupWithNavController(uiToolbar, getNavController(), appBarConfiguration)
    }

    override fun getNavController(): NavController {
        return Navigation.findNavController(requireActivity(), R.id.favorites_host)
    }
}
