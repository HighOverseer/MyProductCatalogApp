package com.fajar.myproductcatalogapp.product_previews.data

import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.ProductPreviewsRemoteDataSource
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.MapperToDomain
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.ProductPreviewsRemoteDataSourceImpl
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.ProductPreviewsAPIService
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.ProductPreviewsAPIServiceImpl
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewsRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun getProductPreviewsDataDIModules(): List<Module> {
    return listOf(
        module {
            single<ProductPreviewsRepository> {
                ProductPreviewsRepositoryImpl(get())
            }

            factory<ProductPreviewsRemoteDataSource> {
                ProductPreviewsRemoteDataSourceImpl(
                    dispatcherProvider = get(),
                    mapperToDomain = get(),
                    apiService = get()
                )
            }

            factory<ProductPreviewsAPIService> {
                ProductPreviewsAPIServiceImpl(get())
            }

            factory<MapperToDomain> { MapperToDomain() }
        }
    )
}