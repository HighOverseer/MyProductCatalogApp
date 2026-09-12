package com.fajar.myproductcatalogapp.product_previews.presentation.di

import com.fajar.myproductcatalogapp.product_previews.presentation.mapper.DUIMapper
import org.koin.dsl.module

internal val mapperModule = module {
    factory { DUIMapper() }
}