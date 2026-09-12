package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation

import com.fajar.myproductcatalogapp.core.domain.model.Page
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ListProductPreviewsDto
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ProductsPreviewItemDto
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem

internal class MapperToDomain {

    fun mapListProductPreviewsDtoToDomain(
        dto: ListProductPreviewsDto
    ): Page<ProductPreviewItem> {
        val items = dto.products.mapNotNull(::mapProductPreviewsDtoToDomain)
        val sanitizedSkip = dto.skip ?: 0
        val sanitizedLimit = dto.limit ?: 0
        val sanitizedTotal = dto.total ?: 0
        val hasNext = dto.run {
            val atTheEnd = sanitizedSkip + sanitizedLimit >= sanitizedTotal
            return@run !atTheEnd
        }

        return Page(
            pagedList = items,
            hasNext = hasNext
        )
    }

    private fun mapProductPreviewsDtoToDomain(
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