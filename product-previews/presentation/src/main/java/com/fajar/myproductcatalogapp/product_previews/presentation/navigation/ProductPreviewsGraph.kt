package com.fajar.myproductcatalogapp.product_previews.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface ProductPreviewsNavigation {
    @Serializable
    data object Graph

    @Serializable
    data object ListRoute
}