package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network

import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ListProductPreviewsDto

internal interface ProductPreviewsAPIService {
    suspend fun getAllProductPreviews(): ListProductPreviewsDto
}