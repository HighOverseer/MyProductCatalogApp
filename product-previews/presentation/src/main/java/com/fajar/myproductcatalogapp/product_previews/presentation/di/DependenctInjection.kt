package com.fajar.myproductcatalogapp.product_previews.presentation.di

import org.koin.core.module.Module

fun getProductPreviewsPresentationDIModules(): List<Module> {
    return listOf(
        viewModelModule,
        mapperModule
    )
}