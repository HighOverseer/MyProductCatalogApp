package com.fajar.myproductcatalogapp.product_detail.presentation.di

import com.fajar.myproductcatalogapp.product_detail.presentation.mapper.DUIMapper
import org.koin.dsl.module

internal val mapperModule = module {
    factory { DUIMapper(duiDateFormatter = get()) }
}