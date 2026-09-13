package com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network

import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.dto.ProductDetailDto

internal interface ProductDetailAPIService {
    suspend fun getProductDetail(productId: Long): ProductDetailDto
}