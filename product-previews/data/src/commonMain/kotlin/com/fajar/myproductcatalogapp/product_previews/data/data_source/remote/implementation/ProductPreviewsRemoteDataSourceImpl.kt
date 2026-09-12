package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation

import com.fajar.myproductcatalogapp.core.common.contract.DispatcherProvider
import com.fajar.myproductcatalogapp.core.data.network.callApiFromNetwork
import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.ProductPreviewsRemoteDataSource
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.ProductPreviewsAPIService
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem
import kotlinx.coroutines.withContext

internal class ProductPreviewsRemoteDataSourceImpl(
    private val apiService: ProductPreviewsAPIService,
    private val dispatcherProvider: DispatcherProvider,
    private val mapperToDomain: MapperToDomain
) : ProductPreviewsRemoteDataSource {

    override suspend fun getProductPreviews(
        query: String,
        pageSize: Int,
        pageOffset: Int
    ): Result<List<ProductPreviewItem>, DataError> =
        withContext(dispatcherProvider.default) {
            callApiFromNetwork(
                execute = {
                    if (query.isBlank()) {
                        apiService.getRandomProductPreviews(
                            pageSize = pageSize,
                            pageOffset = pageOffset
                        )
                    } else apiService.getProductPreviewsByQuery(
                        query = query,
                        pageSize = pageSize,
                        pageOffset = pageOffset
                    )
                },
                mapResponseToResultData = { responseDto ->
                    responseDto.products.mapNotNull(
                        transform = mapperToDomain::mapProductPreviewsDtoToDomain
                    )
                },
            )
        }
}