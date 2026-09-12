package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote

import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem

internal interface ProductPreviewsRemoteDataSource {
    suspend fun getProductPreviews(
        query: String,
        pageSize: Int,
        pageOffset: Int,
    ): Result<List<ProductPreviewItem>, DataError>
}