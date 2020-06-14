package com.makzimi.finmarket.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import com.makzimi.finmarket.App
import com.makzimi.finmarket.BuildConfig
import com.makzimi.finmarket.R
import com.makzimi.finmarket.data.AppDatabase
import com.makzimi.finmarket.network.news.NewsService
import com.makzimi.finmarket.network.stocks.StocksService
import javax.inject.Singleton
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: App) = app.applicationContext

    @Provides
    @Named("stocksColumnCount")
    fun provideStocksColumnCount(context: Context) =
        context.resources.getInteger(R.integer.stocks_column_count)

    @Singleton
    @Provides
    @Named("LoggingInterceptor")
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Singleton
    @Provides
    @Named("StocksApiInterceptor")
    fun provideStocksApiInterceptor(): Interceptor {
        return Interceptor {
            val newRequest: Request =
                it.request()
                    .newBuilder()
                    .url(
                        it.request()
                            .url()
                            .newBuilder()
                            .addQueryParameter(
                                StocksService.API_KEY_NAME,
                                BuildConfig.STOCKS_API_TOKEN
                            )
                            .build()
                    )
                    .build()
            it.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    @Named("NewsApiInterceptor")
    fun provideNewsApiInterceptor(): Interceptor {
        return Interceptor {
            val newRequest: Request =
                it.request()
                    .newBuilder()
                    .url(
                        it.request()
                            .url()
                            .newBuilder()
                            .addQueryParameter(
                                NewsService.API_KEY_NAME,
                                BuildConfig.NEWS_API_TOKEN
                            )
                            .build()
                    )
                    .build()
            it.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    @Named("OkHttpClientStocks")
    fun provideOkHttpClientStocks(
        @Named("LoggingInterceptor") loggingInterceptor: Interceptor,
        @Named("StocksApiInterceptor") stocksInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(stocksInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("OkHttpClientNews")
    fun provideOkHttpClientNews(
        @Named("LoggingInterceptor") loggingInterceptor: Interceptor,
        @Named("NewsApiInterceptor") newsApiInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(newsApiInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("StocksRetrofit")
    fun provideStocksRetrofit(@Named("OkHttpClientStocks") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(StocksService.ENDPOINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @Named("NewsRetrofit")
    fun provideNewsRetrofit(@Named("OkHttpClientNews") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(NewsService.ENDPOINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideStocksService(@Named("StocksRetrofit") retrofit: Retrofit) =
        retrofit.create(StocksService::class.java)

    @Singleton
    @Provides
    fun provideNewsService(@Named("NewsRetrofit") retrofit: Retrofit) =
        retrofit.create(NewsService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(app: App) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideStockListDao(db: AppDatabase) = db.stockListDao()

    @Singleton
    @Provides
    fun provideStockDetailsDao(db: AppDatabase) = db.stockDetailsDao()

    @Singleton
    @Provides
    fun provideFavoritesDao(db: AppDatabase) = db.favoritesDao()

    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase) = db.newsDao()

    @Singleton
    @Provides
    fun provideGlideRequestOptions() =
        RequestOptions.placeholderOf(R.drawable.ic_image_placeholder)
            .error(R.drawable.ic_image_error)

    @Singleton
    @Provides
    fun provideGlideRequestManager(context: Context, requestOptions: RequestOptions) =
        Glide.with(context).setDefaultRequestOptions(requestOptions)


}