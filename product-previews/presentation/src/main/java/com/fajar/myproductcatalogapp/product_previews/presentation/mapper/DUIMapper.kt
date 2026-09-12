package com.fajar.myproductcatalogapp.product_previews.presentation.mapper

import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI

internal class DUIMapper {
    fun mapProductPreviewItemToDUI(
        data: ProductPreviewItem
    ): ProductPreviewItemDUI {
        return data.run {
            val displayPrice = $$"$$$price"
            val displayDiscountPercentage = "${discountPercentage * 100}%"

            ProductPreviewItemDUI(
                id = id,
                title = title,
                thumbnailImageUrl = thumbnailImageUrl,
                displayPrice = displayPrice,
                displayDiscountPercentage = displayDiscountPercentage
            )
        }
    }
}