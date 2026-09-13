package com.fajar.myproductcatalogapp.product_detail.data.data_source.remote

import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetail

internal interface ProductDetailRemoteDataSource {
    suspend fun getProductDetail(
        productId: Long
    ): Result<ProductDetail, DataError>
}