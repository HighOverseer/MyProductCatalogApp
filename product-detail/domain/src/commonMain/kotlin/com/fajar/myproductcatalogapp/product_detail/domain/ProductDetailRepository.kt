package com.fajar.myproductcatalogapp.product_detail.domain

import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Result

interface ProductDetailRepository {
    suspend fun getProductDetail(
        productId: Long
    ): Result<ProductDetail, DataError>
}