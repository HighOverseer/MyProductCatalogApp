package com.fajar.myproductcatalogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fajar.myproductcatalogapp.core.ui.theme.MyProductCatalogAppTheme
import com.fajar.myproductcatalogapp.core.ui.utils.navigateSafely
import com.fajar.myproductcatalogapp.product_detail.presentation.navigation.ProductDetailNavigation
import com.fajar.myproductcatalogapp.product_detail.presentation.navigation.productDetailNavGraph
import com.fajar.myproductcatalogapp.product_previews.presentation.navigation.ProductPreviewsNavigation
import com.fajar.myproductcatalogapp.product_previews.presentation.navigation.productPreviewsNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyProductCatalogAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = ProductPreviewsNavigation.Graph
                ) {
                    productPreviewsNavGraph(
                        openProductDetail = { productId ->
                            navController.navigateSafely(
                                ProductDetailNavigation.Graph(
                                    productId = productId
                                )
                            )
                        }
                    )
                    productDetailNavGraph(navController = navController)
                }
            }
        }
    }
}