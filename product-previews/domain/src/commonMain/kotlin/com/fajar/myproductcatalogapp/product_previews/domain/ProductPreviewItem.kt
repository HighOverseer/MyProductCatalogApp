package com.fajar.myproductcatalogapp.product_previews.domain

data class ProductPreviewItem(
    val id: Long,
    val title: String,
    val thumbnailImageUrl: String,
    val price: Double,
    val discountPercentage: Double
)