package com.fajar.myproductcatalogapp.product_previews.presentation.mapper

import com.fajar.myproductcatalogapp.core.ui.utils.calculateAfterPercentage
import com.fajar.myproductcatalogapp.core.ui.utils.formatDecimalPlaces
import com.fajar.myproductcatalogapp.core.ui.utils.toDisplayNominal
import com.fajar.myproductcatalogapp.core.ui.utils.toDisplayPercentage
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewItem
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI

internal class DUIMapper {
    fun mapProductPreviewItemToDUI(
        data: ProductPreviewItem
    ): ProductPreviewItemDUI {
        return data.run {
            val displayPriceBeforeDiscount = price.toDisplayNominal()
            val displayDiscountPercentage = discountPercentage.toDisplayPercentage()
            val priceAfterDiscount = price.calculateAfterPercentage(discountPercentage)
            val displayPriceAfterDiscount = priceAfterDiscount
                .formatDecimalPlaces(decimalPlaces = 2)
                .toDisplayNominal()

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