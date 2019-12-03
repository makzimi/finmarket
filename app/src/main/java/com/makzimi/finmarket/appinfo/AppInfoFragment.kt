package com.makzimi.finmarket.appinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makzimi.finmarket.BuildConfig

import com.makzimi.finmarket.R
import com.makzimi.finmarket.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_app_info.*
import kotlinx.android.synthetic.main.fragment_catalog_home.uiToolbar

class AppInfoFragment : BaseFragment() {

    companion object {
        fun newInstance() : AppInfoFragment {
            return AppInfoFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_app_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uiToolbar.title = getString(R.string.information)
        uiVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
    }

    override fun onBackPressed(): Boolean {
        return false
    }

}
