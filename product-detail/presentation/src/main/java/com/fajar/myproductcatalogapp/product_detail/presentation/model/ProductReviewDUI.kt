package com.fajar.myproductcatalogapp.product_detail.presentation.model

import androidx.compose.runtime.Immutable
import com.fajar.myproductcatalogapp.core.domain.model.Rating

@Immutable
internal data class ProductReviewDUI(
    val displayRating: String = "-",
    val comment: String = "-",
    val displayPostedDate: String = "-",
    val reviewerName: String = "-",
    val rating: Rating = Rating(0.0, 1.0),
    val reviewerEmail: String = "-"
)