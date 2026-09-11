package com.fajar.myproductcatalogapp.core.common

import com.fajar.myproductcatalogapp.core.common.contract.DispatcherProvider
import com.fajar.myproductcatalogapp.core.common.contract.getDispatcherProvider
import org.koin.core.module.Module
import org.koin.dsl.module

fun getCoreCommonDIModules(): List<Module> {
    return listOf(
        module {
            single<DispatcherProvider> { getDispatcherProvider() }
        }
    )
}