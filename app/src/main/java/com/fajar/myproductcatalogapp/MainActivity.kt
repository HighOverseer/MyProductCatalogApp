package com.fajar.myproductcatalogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fajar.myproductcatalogapp.core.ui.theme.MyProductCatalogAppTheme
import com.fajar.myproductcatalogapp.product_previews.presentation.navigation.ProductPreviewsNavigation
import com.fajar.myproductcatalogapp.product_previews.presentation.navigation.productPreviewsNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
                        openProductDetail = {
                            // TODO add navigation to Product Detail Graph
                        },
                        onNavigateUp = ::finishAndRemoveTask
                    )
                }
            }
        }
    }
}