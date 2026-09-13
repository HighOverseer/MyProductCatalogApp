package com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation

import com.fajar.myproductcatalogapp.core.common.contract.DispatcherProvider
import com.fajar.myproductcatalogapp.core.data.network.callApiFromNetwork
import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.core.domain.model.RootNetworkError
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.ProductDetailRemoteDataSource
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.ProductDetailAPIService
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetail
import kotlinx.coroutines.withContext

internal class ProductDetailRemoteDataSourceImpl(
    private val apiService: ProductDetailAPIService,
    private val dispatcherProvider: DispatcherProvider,
    private val mapperToDomain: MapperToDomain
) : ProductDetailRemoteDataSource {

    override suspend fun getProductDetail(productId: Long): Result<ProductDetail, DataError> {
        return withContext(dispatcherProvider.default) {
            callApiFromNetwork(
                execute = {
                    apiService.getProductDetail(productId)
                },
                mapResponseToResultData = { responseDto ->
                    mapperToDomain
                        .mapProductDetailDtoToDomain(responseDto)
                        ?: return@withContext Result.Error(RootNetworkError.UNEXPECTED_ERROR)
                }
            )
        }
    }
}