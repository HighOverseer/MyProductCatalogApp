package com.fajar.myproductcatalogapp.product_detail.presentation.model

import androidx.compose.runtime.Immutable
import com.fajar.myproductcatalogapp.core.domain.model.Rating
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlin.time.Clock

@Immutable
internal data class ProductDetailDUI(
    val id: Long = Clock.System.now().toEpochMilliseconds(),
    val title: String = "-",
    val description: String = "-",
    val displayPriceBeforeDiscount: String = "$-",
    val displayPriceAfterDiscount: String = "$-",
    val displayDiscountPercentage: String = "-%",
    val overallRating: Rating? = null,
    val displayOverallRating: String = "-",
    val imageUrls: ImmutableList<String> = persistentListOf(),
    val reviews: ImmutableList<ProductReviewDUI> = persistentListOf()
)

