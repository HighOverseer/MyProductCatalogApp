package com.fajar.myproductcatalogapp.product_previews.presentation.di

import com.fajar.myproductcatalogapp.product_previews.presentation.ListProductPreviewsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel {
        ListProductPreviewsViewModel(
            repository = get(),
            dispatcherProvider = get(),
            duiMapper = get(),
            defaultErrorMapper = get()
        )
    }
}