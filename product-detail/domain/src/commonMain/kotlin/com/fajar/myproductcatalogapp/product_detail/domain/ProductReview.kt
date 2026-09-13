package com.fajar.myproductcatalogapp.product_detail.domain

import com.fajar.myproductcatalogapp.core.domain.model.Rating

data class ProductReview(
    val rating: Rating,
    val comment: String,
    val postedTimestamp: Long,
    val reviewerName: String,
    val reviewerEmail: String
)