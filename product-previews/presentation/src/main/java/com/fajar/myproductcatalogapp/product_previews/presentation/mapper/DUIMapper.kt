package com.fajar.myproductcatalogapp.product_previews.presentation.mapper

import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI

internal class DUIMapper {
    fun mapProductPreviewItemToDUI(
        data: ProductPreviewItem
    ): ProductPreviewItemDUI {
        return data.run {
            val displayPriceBeforeDiscount = $$"$$$price"
            val displayDiscountPercentage = "$discountPercentage%"
            val priceAfterDiscount = run {
                val normalizedDiscount = (discountPercentage / 100)
                val result = (1 - normalizedDiscount).coerceIn(0.0, 1.0) * price
                return@run result
            }
            val displayPriceAfterDiscount = "%.2f".format(priceAfterDiscount)
                .let { $$"$$${it}" }

            ProductPreviewItemDUI(
                id = id,
                title = title,
                thumbnailImageUrl = thumbnailImageUrl,
                displayPriceBeforeDiscount = displayPriceBeforeDiscount,
                displayDiscountPercentage = displayDiscountPercentage,
                displayPriceAfterDiscount = displayPriceAfterDiscount
            )
        }
    }
}