package com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation

import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.dto.ProductDetailDto
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.dto.ProductReviewDto
import com.fajar.myproductcatalogapp.product_detail.domain.ProductDetail
import com.fajar.myproductcatalogapp.product_detail.domain.ProductReview
import com.fajar.myproductcatalogapp.core.domain.model.Rating
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

internal class MapperToDomain {

    fun mapProductDetailDtoToDomain(
        dto: ProductDetailDto
    ): ProductDetail? {
        return dto.run {
            ProductDetail(
                id = id?.toLong() ?: return@run null,
                title = title ?: return null,
                description = description ?: "-",
                price = price ?: return null,
                discountPercentage = discountPercentage ?: return null,
                overallRating = overallRating?.toRating(),
                imageUrls = images.mapNotNull { it },
                reviews = reviews
                    .mapNotNull { it }
                    .mapNotNull(::mapProductReviewDtoToDomain)
            )
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun mapProductReviewDtoToDomain(
        dto: ProductReviewDto
    ): ProductReview? {
        return dto.run {
            val postedTimestamp = date?.let { date ->
                Instant.parseOrNull(date)
                    ?.toEpochMilliseconds()
            }

            ProductReview(
                rating = rating?.toDouble()?.toRating() ?: return null,
                comment = comment ?: "-",
                postedTimestamp = postedTimestamp ?: return null,
                reviewerName = reviewerName ?: return null,
                reviewerEmail = reviewerEmail ?: "-",
            )
        }
    }

    private fun Double.toRating(
        maxScore: Double = 5.0
    ): Rating {
        return Rating(relativeScore = this, maxScore = maxScore)
    }
}