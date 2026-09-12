package com.fajar.myproductcatalogapp.product_previews.domain

import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Page
import com.fajar.myproductcatalogapp.core.domain.model.Result

interface ProductPreviewsRepository {
    suspend fun getProductPreviews(
        query: String = "",
        size: Int,
        offset: Int
    ): Result<Page<ProductPreviewItem>, DataError>
}