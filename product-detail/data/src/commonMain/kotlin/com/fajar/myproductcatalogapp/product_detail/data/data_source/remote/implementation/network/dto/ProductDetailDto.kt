package com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductDetailDto(
    @SerialName("id")
    val id: Int? = null,

    @SerialName("images")
    val images: List<String?> = emptyList(),

    @SerialName("rating")
    val overallRating: Double? = null,

    @SerialName("description")
    val description: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("price")
    val price: Double? = null,

    @SerialName("discountPercentage")
    val discountPercentage: Double? = null,

    @SerialName("reviews")
    val reviews: List<ProductReviewDto?> = emptyList(),

    )