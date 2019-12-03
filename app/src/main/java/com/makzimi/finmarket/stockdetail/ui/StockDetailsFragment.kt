package com.makzimi.finmarket.stockdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.NavOptions
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

import com.makzimi.finmarket.common.RepositoryResult
import com.makzimi.finmarket.databinding.FragmentStockDetailsBinding
import com.makzimi.finmarket.model.StockEntity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_stock_details.*
import javax.inject.Inject
import android.text.util.Linkify
import com.makzimi.finmarket.R
import com.makzimi.finmarket.common.ext.fromDateToDate
import com.makzimi.finmarket.databinding.ItemArticleBinding
import com.makzimi.finmarket.model.NewsEntity
import android.content.Intent

import android.net.Uri


class StockDetailsFragment : DaggerFragment() {

    private val args: StockDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    lateinit var viewModel: StockDetailsViewModel

    @Inject
    lateinit var fragmentNavOptions: NavOptions

    private lateinit var binding: FragmentStockDetailsBinding

    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStockDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory)[StockDetailsViewModel::class.java]
        viewModel.symbol = args.symbol

        subscribeUI()
        viewModel.observeStockDetails()

        uiFab.setOnClickListener { view ->
            view.tag?.let{
                if (it is StockEntity) {
                    viewModel.addToFavourites(it, !isFavorite)
                }
            }
        }
    }

    private fun subscribeUI() {
        viewModel.companyInfo.observe(viewLifecycleOwner) {
            when(it.status) {
                RepositoryResult.Status.LOADING -> {
                    showUILoading()
                }
                RepositoryResult.Status.SUCCESS -> {
                    if(it.data == null) {
                        showUIMessage()
                        uiMessage.text = getString(R.string.loading_error)
                    } else {
                        showUIContent()
                        fillContent(it.data)
                    }
                }
                RepositoryResult.Status.ERROR -> {
                    showUIMessage()
                    uiMessage.text = getString(R.string.loading_error)
                }
            }
        }

        viewModel.isFavorites.observe(viewLifecycleOwner) {
            isFavorite = it
            updateFab()
        }

        viewModel.news.observe(viewLifecycleOwner) {
            when(it.status) {
                RepositoryResult.Status.LOADING -> {
                    showUINewsLoading()
                }
                RepositoryResult.Status.SUCCESS -> {
                    if(it.data == null) {
                        showUINewsEmpty()
                        uiMessage.text = getString(R.string.loading_error)
                    } else {
                        showUINewsContent()
                        fillNewsContent(it.data)
                    }
                }
                RepositoryResult.Status.ERROR -> {
                    showUINewsEmpty()
                    uiMessage.text = getString(R.string.loading_error)
                }
            }
        }
    }

    private fun showUILoading() {
        uiContent.visibility = View.GONE
        uiProgressBar.visibility = View.VISIBLE
        uiMessage.visibility = View.GONE
    }

    private fun showUIContent() {
        uiContent.visibility = View.VISIBLE
        uiProgressBar.visibility = View.GONE
        uiMessage.visibility = View.GONE
    }

    private fun showUIMessage() {
        uiContent.visibility = View.GONE
        uiProgressBar.visibility = View.GONE
        uiMessage.visibility = View.VISIBLE
    }

    private fun showUINewsLoading() {
        uiNewsHeader.visibility = View.VISIBLE
        uiNewsHeader.text = getString(R.string.news_header_loading)
        uiNewsContent.visibility = View.GONE
    }

    private fun showUINewsEmpty() {
        uiNewsHeader.visibility = View.VISIBLE
        uiNewsHeader.text = getString(R.string.news_header_empty)
        uiNewsContent.visibility = View.GONE
    }

    private fun showUINewsContent() {
        uiNewsHeader.visibility = View.VISIBLE
        uiNewsHeader.text = getString(R.string.news_header_normal)
        uiNewsContent.visibility = View.VISIBLE
    }

    private fun fillContent(stockEntity: StockEntity) {
        Glide.with(this).load(stockEntity.image).into(binding.detailImage)

        setValueOrHide(uiCompanyName, uiCompanyNameLabel, stockEntity.companyName)
        setValueOrHide(uiDescriptioni, uiDescriptioniLabel, stockEntity.description)
        setValueOrHide(uiCEO, uiCEOLabel, stockEntity.ceo)
        setValueOrHide(uiLastDividends, uiLastDividendsLabel, stockEntity.lastDiv.toString())
        setValueOrHide(uiIndustry, uiIndustryLabel, stockEntity.industry)
        setValueOrHide(uiSector, uiSectorLabel, stockEntity.sector)
        setValueOrHide(uiWebsite, uiWebsiteLabel, stockEntity.website)
        Linkify.addLinks(uiWebsite, Linkify.ALL)

        uiFab.tag = stockEntity
    }

    private fun updateFab() {
        //https://issuetracker.google.com/issues/111316656
        uiFab.hide()
        if(isFavorite) {
            uiFab.setImageResource(R.drawable.ic_favorite_24px)
        } else {
            uiFab.setImageResource(R.drawable.ic_favorite_border_24px)
        }
        uiFab.show()
    }

    private fun setValueOrHide(valueView: TextView, labelView: View, value: String?) {
        if(value.isNullOrEmpty()) {
            valueView.visibility = View.GONE
            labelView.visibility = View.GONE
        } else {
            valueView.visibility = View.VISIBLE
            labelView.visibility = View.VISIBLE
            valueView.text = value
        }
    }

    private fun fillNewsContent(news: List<NewsEntity>) {
        uiNewsContent.removeAllViews()
        for(article in news) {
            val articleBinding: ItemArticleBinding = ItemArticleBinding.inflate(this.layoutInflater)
            articleBinding.uiTitle.text = article.title
            articleBinding.uiSource.text = article.source
            articleBinding.uiDate.text = article.publishedAt?.fromDateToDate(
                "yyyy-MM-dd'T'HH:mm:ss",
                "dd MMM yyyy")
            uiNewsContent.addView(articleBinding.root)

            articleBinding.root.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(article.url)
                startActivity(i)
            }
        }
    }

}
