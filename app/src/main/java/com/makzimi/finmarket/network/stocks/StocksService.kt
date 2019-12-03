package com.makzimi.finmarket.network.stocks

import com.makzimi.finmarket.network.stocks.entity.CompanyStockApiEntity
import com.makzimi.finmarket.network.stocks.entity.PriceListApiEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface StocksService {

    companion object {
        const val ENDPOINT = "https://financialmodelingprep.com/"

        // TODO find a better API for Nasdaq stocks
        const val NASDAQ100 = "ATVI,ADBE,AMD,ALGN,ALXN,AMZN,AMGN,AAL,ADI,AAPL,AMAT,ASML,ADSK,ADP,AVGO,BIDU,BIIB,BMRN,CDNS,CELG,CERN,CHKP,CHTR,TCOM,CTAS,CSCO,CTXS,CMCSA,COST,CSX,CTSH,DLTR,EA,EBAY,EXPE,FAST,FB,FISV,GILD,GOOG,GOOGL,HAS,HSIC,ILMN,INCY,INTC,INTU,ISRG,IDXX,JBHT,JD,KLAC,KHC,LRCX,LBTYA,LBTYK,LULU,MELI,MAR,MCHP,MDLZ,MNST,MSFT,MU,MXIM,MYL,UNCH,NTAP,NFLX,NTES,NVDA,NXPI,ORLY,PAYX,PCAR,BKNG,PYPL,PEP,QCOM,REGN,ROST,SIRI,SWKS,SBUX,NLOK,SNPS,TTWO,TSLA,TXN,TMUS,ULTA,UAL,VRSN,VRSK,VRTX,WBA,WDC,WDAY,WYNN,XEL,XLNX"
    }

    @GET("api/v3/stock/real-time-price/$NASDAQ100")
    fun getNasdaqPrices() : Single<PriceListApiEntity>

    @GET("api/v3/stock/real-time-price/{symbols}")
    fun getSpecificPrices(@Path("symbols") symbols: String) : Single<PriceListApiEntity>

    @GET("/api/v3/company/profile/{symbol}")
    fun getCompanyInfo(@Path("symbol") symbol: String) : Single<CompanyStockApiEntity>
}