package com.fajar.myproductcatalogapp.product_detail.data

import com.fajar.myproductcatalogapp.core.domain.model.DataError
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.ProductDetailRemoteDataSource
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetail
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetailRepository

internal class ProductDetailRepositoryImpl(
    private val remoteDataSource: ProductDetailRemoteDataSource
) : ProductDetailRepository {

    override suspend fun getProductDetail(productId: Long): Result<ProductDetail, DataError> {
        return remoteDataSource.getProductDetail(productId)
    }
}