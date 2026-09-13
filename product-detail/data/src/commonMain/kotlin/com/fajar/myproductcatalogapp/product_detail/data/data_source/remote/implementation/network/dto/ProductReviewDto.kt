package com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProductReviewDto(

    @SerialName("date")
    val date: String? = null,

    @SerialName("reviewerName")
    val reviewerName: String? = null,

    @SerialName("reviewerEmail")
    val reviewerEmail: String? = null,

    @SerialName("rating")
    val rating: Int? = null,

    @SerialName("comment")
    val comment: String? = null
)