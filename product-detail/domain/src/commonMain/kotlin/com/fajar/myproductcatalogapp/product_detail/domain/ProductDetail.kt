package com.fajar.myproductcatalogapp.product_detail.domain

import com.fajar.myproductcatalogapp.core.domain.model.Rating

data class ProductDetail(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val overallRating: Rating?,
    val imageUrls: List<String>,
    val reviews: List<ProductReview>
)