package com.fajar.myproductcatalogapp.product_detail.presentation.mapper

import com.fajar.myproductcatalogapp.core.ui.utils.calculateAfterPercentage
import com.fajar.myproductcatalogapp.core.ui.utils.formatDecimalPlaces
import com.fajar.myproductcatalogapp.core.ui.utils.formatter.DefaultDUIDateFormatter
import com.fajar.myproductcatalogapp.core.ui.utils.toDisplay
import com.fajar.myproductcatalogapp.core.ui.utils.toDisplayNominal
import com.fajar.myproductcatalogapp.core.ui.utils.toDisplayPercentage
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetail
import com.fajar.myproductcatalogapp.product_detail.domain.ProductReview
import com.fajar.myproductcatalogapp.product_detail.presentation.model.ProductDetailDUI
import com.fajar.myproductcatalogapp.product_detail.presentation.model.ProductReviewDUI
import kotlinx.collections.immutable.toImmutableList

internal class DUIMapper(
    private val duiDateFormatter: DefaultDUIDateFormatter,
) {

    fun mapProductDetailToDUI(
        data: ProductDetail
    ): ProductDetailDUI {
        return data.run {
            val displayPriceBeforeDiscount = price.toDisplayNominal()
            val displayDiscountPercentage = discountPercentage.toDisplayPercentage()
            val priceAfterDiscount = price.calculateAfterPercentage(discountPercentage)
            val displayPriceAfterDiscount = priceAfterDiscount
                .formatDecimalPlaces(decimalPlaces = 2)
                .toDisplayNominal()
            val displayOverallRating = overallRating
                ?.toDisplay()
                ?: "-"

            ProductDetailDUI(
                id = id,
                title = title,
                description = description,
                displayPriceBeforeDiscount = displayPriceBeforeDiscount,
                displayPriceAfterDiscount = displayPriceAfterDiscount,
                displayDiscountPercentage = displayDiscountPercentage,
                displayOverallRating = displayOverallRating,
                overallRating = overallRating,
                imageUrls = imageUrls.toImmutableList(),
                reviews = reviews.map(::mapProductPreviewToDUI)
                    .toImmutableList()
            )
        }
    }

    private fun mapProductPreviewToDUI(
        data: ProductReview
    ): ProductReviewDUI {
        return data.run {
            ProductReviewDUI(
                displayRating = rating.toDisplay(),
                comment = comment,
                displayPostedDate = duiDateFormatter
                    .formatToDisplayDate(postedTimestamp),
                reviewerName = reviewerName,
                rating = rating,
                reviewerEmail = reviewerEmail
            )
        }
    }

}