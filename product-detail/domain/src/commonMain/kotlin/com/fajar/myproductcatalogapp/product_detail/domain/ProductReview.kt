package com.fajar.myproductcatalogapp.product_detail.domain

data class ProductReview(
    val rating: Int,
    val comment: String,
    val postedDate: Long,
    val reviewerName: String,
    val reviewerEmail: String
)