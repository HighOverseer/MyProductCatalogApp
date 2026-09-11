package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation

import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ProductsPreviewItemDto
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem

internal class MapperToDomain {
    fun mapProductPreviewsDtoToDomain(
        dto: ProductsPreviewItemDto
    ): ProductPreviewItem? {
        return dto.run {
            ProductPreviewItem(
                id = id?.toLong() ?: return null,
                title = title ?: return null,
                thumbnailImageUrl = thumbnail ?: return null,
                price = price ?: return null,
                discountPercentage = discountPercentage ?: return null
            )
        }
    }
}