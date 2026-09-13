package com.fajar.myproductcatalogapp.product_detail.domain

data class ProductDetail(
    val id: Long,
    val title: String,
    val description: String,
    val price: Double,
    val overallRating: Int,
    val imageUrls: List<String>,
    val reviews: List<ProductReview>
)