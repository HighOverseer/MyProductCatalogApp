package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ListProductPreviewsDto(

    @SerialName("total")
    val total: Int? = null,

    @SerialName("limit")
    val limit: Int? = null,

    @SerialName("skip")
    val skip: Int? = null,

    @SerialName("products")
    val products: List<ProductsPreviewItemDto> = emptyList()
)

@Serializable
internal data class ProductsPreviewItemDto(

    @SerialName("thumbnail")
    val thumbnail: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("discountPercentage")
    val discountPercentage: Double? = null,

    @SerialName("price")
    val price: Double? = null,

    @SerialName("id")
    val id: Int? = null,
)