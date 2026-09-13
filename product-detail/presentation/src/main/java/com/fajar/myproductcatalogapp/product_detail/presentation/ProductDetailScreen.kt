package com.fajar.myproductcatalogapp.product_detail.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fajar.myproductcatalogapp.core.domain.model.Rating
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.core.ui.components.GeneralDialog
import com.fajar.myproductcatalogapp.core.ui.theme.Black10
import com.fajar.myproductcatalogapp.core.ui.theme.MyProductCatalogAppTheme
import com.fajar.myproductcatalogapp.product_detail.presentation.content.ProductDetailMainContent
import com.fajar.myproductcatalogapp.product_detail.presentation.content.ShimmeringProductDetailContent
import com.fajar.myproductcatalogapp.product_detail.presentation.model.ProductDetailDUI
import com.fajar.myproductcatalogapp.product_detail.presentation.model.ProductReviewDUI
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    onNavigateUp: () -> Unit = { }
) {
    LaunchedEffect(Unit) {
        viewModel.initializeIfNeeded()
    }

    val uiState by viewModel.uiState
        .collectAsStateWithLifecycle()

    ProductDetailScreen(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
    )
}

@Composable
private fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    uiState: ProductDetailUIState = ProductDetailUIState(),
    onNavigateUp: () -> Unit = { },
) {
    val scrollState = rememberScrollState()

    uiState.errorUserMessage?.let { errorUserMessage ->
        GeneralDialog(
            title = stringResource(R.string.error),
            message = errorUserMessage.getValue(),
            onClick = onNavigateUp
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {

        Spacer(Modifier.height(32.dp))

        IconButton(
            onClick = onNavigateUp
        ) {
            Icon(
                modifier = Modifier
                    .width(21.dp)
                    .height(20.dp),
                imageVector = ImageVector
                    .vectorResource(R.drawable.ic_arrow_left),
                contentDescription = null,
                tint = Black10
            )
        }

        Spacer(Modifier.height(16.dp))

        if (uiState.isLoading) {
            ShimmeringProductDetailContent()
        } else ProductDetailMainContent(dui = uiState.productDetailDUI)
    }
}


@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    MyProductCatalogAppTheme {
        ProductDetailScreen(
            uiState = ProductDetailUIState(
                isLoading = false,
                errorUserMessage = null,
                productDetailDUI = ProductDetailDUI(
                    title = "Headset Nexus A1",
                    description = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum  Lorem ipsum Lorem ipsum",
                    displayPriceBeforeDiscount = "$10.0",
                    displayPriceAfterDiscount = "$9.0",
                    displayDiscountPercentage = "10%",
                    displayOverallRating = "3.5/5",
                    overallRating = Rating(3.5, 5.0),
                    imageUrls = persistentListOf("image.com"),
                    reviews = remember {
                        List(5) {
                            ProductReviewDUI(
                                rating = Rating(3.5, 5.0),
                                displayRating = "3.5/5",
                                comment = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem",
                                displayPostedDate = "12 Jan 2022",
                                reviewerName = "Andi Daud",
                                reviewerEmail = "andidaud32@gmail.com"
                            )
                        }.toImmutableList()
                    }
                )
            )
        )
    }
}