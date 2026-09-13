package com.fajar.myproductcatalogapp.product_detail.presentation.di

import com.fajar.myproductcatalogapp.product_detail.presentation.ProductDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { (productId: Long) ->
        ProductDetailViewModel(
            repository = get(),
            duiMapper = get(),
            defaultErrorMapper = get(),
            productId = productId
        )
    }
}