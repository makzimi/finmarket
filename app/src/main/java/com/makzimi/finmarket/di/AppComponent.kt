package com.makzimi.finmarket.di

import com.makzimi.finmarket.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import dagger.BindsInstance


@Singleton
@Component(
    modules = [ AndroidSupportInjectionModule::class,
                AppModule::class,
                ActivityBuildersModule::class,
                ViewModelFactoryModule::class]
)
interface AppComponent: AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: App): AppComponent
    }
}