package com.fajar.myproductcatalogapp.product_previews.presentation.model

import androidx.compose.runtime.Immutable
import kotlin.time.Clock

@Immutable
internal data class ProductPreviewItemDUI(
    val id: Long = Clock.System.now().toEpochMilliseconds(),
    val title: String = "-",
    val thumbnailImageUrl: String = "-",
    val displayPriceBeforeDiscount: String = "$-",
    val displayPriceAfterDiscount: String = "$-",
    val displayDiscountPercentage: String = "-"
)