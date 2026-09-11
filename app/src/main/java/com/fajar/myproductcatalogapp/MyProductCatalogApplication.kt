package com.fajar.myproductcatalogapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyProductCatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyProductCatalogApplication)
            modules(appLevelModule)
        }
    }
}