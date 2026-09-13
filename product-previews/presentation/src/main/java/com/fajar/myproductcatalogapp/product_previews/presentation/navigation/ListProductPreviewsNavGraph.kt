package com.fajar.myproductcatalogapp.product_previews.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fajar.myproductcatalogapp.product_previews.presentation.ListProductPreviewsScreen
import org.koin.compose.viewmodel.koinViewModel

fun NavGraphBuilder.productPreviewsNavGraph(
    openProductDetail: (Long) -> Unit = { },
) {
    navigation<ProductPreviewsNavigation.Graph>(
        startDestination = ProductPreviewsNavigation.ListRoute
    ) {
        composable<ProductPreviewsNavigation.ListRoute> {
            ListProductPreviewsScreen(
                viewModel = koinViewModel(),
                openProductDetail = openProductDetail,
            )
        }
    }
}