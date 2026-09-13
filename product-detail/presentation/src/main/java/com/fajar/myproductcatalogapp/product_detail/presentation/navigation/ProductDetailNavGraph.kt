package com.fajar.myproductcatalogapp.product_detail.presentation.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.fajar.myproductcatalogapp.core.ui.utils.navigateUpSafely
import com.fajar.myproductcatalogapp.product_detail.presentation.ProductDetailScreen
import com.fajar.myproductcatalogapp.product_detail.presentation.ProductDetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.productDetailNavGraph(
    navController: NavHostController
) {
    navigation<ProductDetailNavigation.Graph>(
        startDestination = ProductDetailNavigation.ProductDetailRoute
    ) {
        composable<ProductDetailNavigation.ProductDetailRoute> { entry ->
            val navGraphBackStackEntry = remember(entry) {
                navController.getBackStackEntry<ProductDetailNavigation.Graph>()
            }
            val navGraphRoute = navGraphBackStackEntry.toRoute<ProductDetailNavigation.Graph>()
            val viewModel: ProductDetailViewModel = koinViewModel {
                parametersOf(navGraphRoute.productId)
            }

            ProductDetailScreen(
                viewModel = viewModel,
                onNavigateUp = navController::navigateUpSafely
            )
        }
    }
}