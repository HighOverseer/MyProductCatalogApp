package com.fajar.myproductcatalogapp

import com.fajar.myproductcatalogapp.core.data.contract.AppConfiguration

internal class AndroidAppConfiguration : AppConfiguration {
    override val baseUrl: String
        get() = BuildConfig.BASE_URL
    override val isDebug: Boolean
        get() = BuildConfig.DEBUG
}