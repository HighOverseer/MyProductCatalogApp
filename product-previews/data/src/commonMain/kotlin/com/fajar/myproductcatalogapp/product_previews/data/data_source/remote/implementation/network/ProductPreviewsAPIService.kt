package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network

import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ListProductPreviewsDto

internal interface ProductPreviewsAPIService {
    suspend fun getRandomProductPreviews(pageSize: Int, pageOffset: Int): ListProductPreviewsDto
    suspend fun getProductPreviewsByQuery(
        query: String,
        pageSize: Int,
        pageOffset: Int
    ): ListProductPreviewsDto
}