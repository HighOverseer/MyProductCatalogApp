package com.fajar.myproductcatalogapp.product_detail.data

import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.ProductDetailRemoteDataSource
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.MapperToDomain
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.ProductDetailRemoteDataSourceImpl
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.ProductDetailAPIService
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.ProductDetailAPIServiceImpl
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetailRepository
import org.koin.core.module.Module
import org.koin.dsl.module

fun getProductDetailDIModules(): List<Module> {
    return listOf(
        module {
            single<ProductDetailRepository> {
                ProductDetailRepositoryImpl(
                    remoteDataSource = get()
                )
            }

            factory<ProductDetailRemoteDataSource> {
                ProductDetailRemoteDataSourceImpl(
                    apiService = get(),
                    dispatcherProvider = get(),
                    mapperToDomain = get()
                )
            }

            factory<ProductDetailAPIService> {
                ProductDetailAPIServiceImpl(httpClient = get())
            }

            factory<MapperToDomain> { MapperToDomain() }
        }
    )
}