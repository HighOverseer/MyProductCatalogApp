package com.fajar.myproductcatalogapp.product_previews.presentation.model

import androidx.compose.runtime.Immutable
import kotlin.time.Clock

@Immutable
data class ProductPreviewItemDUI(
    val id: Long = Clock.System.now().toEpochMilliseconds(),
    val title: String = "-",
    val thumbnailImageUrl: String = "-",
    val displayPrice: String = "$-",
    val displayDiscountPercentage: String = "-"
)