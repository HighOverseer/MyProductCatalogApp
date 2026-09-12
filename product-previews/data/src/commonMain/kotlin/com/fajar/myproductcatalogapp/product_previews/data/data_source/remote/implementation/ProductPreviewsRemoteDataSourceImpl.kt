package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation

import com.fajar.myproductcatalogapp.core.common.contract.DispatcherProvider
import com.fajar.myproductcatalogapp.core.data.network.callApiFromNetwork
import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Page
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
        size: Int,
        offset: Int
    ): Result<Page<ProductPreviewItem>, DataError> =
        withContext(dispatcherProvider.default) {
            callApiFromNetwork(
                execute = {
                    if (query.isBlank()) {
                        apiService.getRandomProductPreviews(
                            limit = size,
                            skip = offset
                        )
                    } else apiService.getProductPreviewsByQuery(
                        query = query,
                        limit = size,
                        skip = offset
                    )
                },
                mapResponseToResultData = { responseDto ->
                    mapperToDomain.mapListProductPreviewsDtoToDomain(
                        dto = responseDto
                    )
                },
            )
        }
}