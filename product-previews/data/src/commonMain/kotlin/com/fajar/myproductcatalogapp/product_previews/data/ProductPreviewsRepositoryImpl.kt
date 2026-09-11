package com.fajar.myproductcatalogapp.product_previews.data

import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.ProductPreviewsRemoteDataSource
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewsRepository

internal class ProductPreviewsRepositoryImpl(
    private val remoteDataSource: ProductPreviewsRemoteDataSource
) : ProductPreviewsRepository {

    override suspend fun getAllProductPreviews(): Result<List<ProductPreviewItem>, DataError> {
        return remoteDataSource.getAllProductPreviews()
    }
}