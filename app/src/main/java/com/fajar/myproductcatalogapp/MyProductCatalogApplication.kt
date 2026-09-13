package com.fajar.myproductcatalogapp

import android.app.Application
import com.fajar.myproductcatalogapp.core.common.getCoreCommonDIModules
import com.fajar.myproductcatalogapp.core.data.getCoreDataDIModules
import com.fajar.myproductcatalogapp.core.ui.di.getCoreUIDIModules
import com.fajar.myproductcatalogapp.product_detail.data.getProductDetailDIModules
import com.fajar.myproductcatalogapp.product_previews.data.getProductPreviewsDataDIModules
import com.fajar.myproductcatalogapp.product_previews.presentation.di.getProductPreviewsPresentationDIModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyProductCatalogApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyProductCatalogApplication)
            modules(appLevelDIModule)
            modules(getCoreCommonDIModules())
            modules(getCoreDataDIModules())
            modules(getCoreUIDIModules())
            modules(getProductPreviewsDataDIModules())
            modules(getProductPreviewsPresentationDIModules())
            modules(getProductDetailDIModules())
        }
    }
}