package com.makzimi.finmarket.favorites.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.makzimi.finmarket.R
import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.databinding.FragmentCommonListBinding
import com.makzimi.finmarket.model.StockEntity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_common_list.*
import javax.inject.Inject
import javax.inject.Provider

class FavoritesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    lateinit var viewModel: FavoritesViewModel

    @Inject
    lateinit var listItemDecorator: RecyclerView.ItemDecoration

    @Inject
    lateinit var layoutManagerProvider: Provider<RecyclerView.LayoutManager>

    @Inject
    lateinit var fragmentNavOptions: NavOptions

    private lateinit var favoritesListAdapter: FavoritesListAdapter
    private lateinit var binding: FragmentCommonListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommonListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        favoritesListAdapter = FavoritesListAdapter()
        favoritesListAdapter.onCategoryClick = this::onCategoryClickCallback
        binding.uiRecyclerView.adapter = favoritesListAdapter
        setLayoutManager()

        binding.uiSwipeRefreshLayout.setOnRefreshListener {
            if(!viewModel.fetchFavorites()) {
                binding.uiSwipeRefreshLayout.isRefreshing = false
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)[FavoritesViewModel::class.java]
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.stocks.observe(viewLifecycleOwner) {
            when(it.status) {
                RepositoryResult.Status.LOADING -> {
                    showUILoading()
                }
                RepositoryResult.Status.SUCCESS -> {
                    if(it.data.isNullOrEmpty()) {
                        showUIMessage()
                        uiMessage.text = getString(R.string.empty_list)
                    } else {
                        showUIContent()
                        favoritesListAdapter.submitList(it.data)
                    }
                }
                RepositoryResult.Status.ERROR -> {
                    showUIMessage()
                    uiMessage.text = getString(R.string.loading_error)
                }
            }
            binding.uiSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showUILoading() {
        uiRecyclerView.visibility = View.GONE
        uiProgressBar.visibility = View.VISIBLE
        uiMessage.visibility = View.GONE
    }

    private fun showUIContent() {
        uiRecyclerView.visibility = View.VISIBLE
        uiProgressBar.visibility = View.GONE
        uiMessage.visibility = View.GONE
    }

    private fun showUIMessage() {
        uiRecyclerView.visibility = View.GONE
        uiProgressBar.visibility = View.GONE
        uiMessage.visibility = View.VISIBLE
    }

    private fun setLayoutManager() {
        binding.uiRecyclerView.addItemDecoration(listItemDecorator)
        binding.uiRecyclerView.layoutManager = layoutManagerProvider.get()
    }

    private fun onCategoryClickCallback(stockEntity: StockEntity) {
        findNavController().navigate(
            FavoritesFragmentDirections.actionFavoritesDestinationToStockDetailsDestination(
                stockEntity.symbol), fragmentNavOptions)
    }
}
