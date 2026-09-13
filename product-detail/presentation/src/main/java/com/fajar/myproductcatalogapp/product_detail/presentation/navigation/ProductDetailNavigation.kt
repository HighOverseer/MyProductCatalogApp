package com.fajar.myproductcatalogapp.product_detail.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ProductDetailNavigation {
    @Serializable
    data class Graph(val productId: Long)

    @Serializable
    data object ProductDetailRoute
}