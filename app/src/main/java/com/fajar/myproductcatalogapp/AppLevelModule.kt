package com.fajar.myproductcatalogapp

import com.fajar.myproductcatalogapp.core.data.contract.AppConfiguration
import org.koin.dsl.module

internal val appLevelModule = module {
    single<AppConfiguration> {
        AndroidAppConfiguration()
    }
}