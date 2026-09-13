package com.fajar.myproductcatalogapp.product_detail.presentation.di

import org.koin.core.module.Module

fun getProductDetailPresentationDIModules(): List<Module> {
    return listOf(
        viewModelModule,
        mapperModule
    )
}