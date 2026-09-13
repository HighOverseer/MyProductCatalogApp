package com.fajar.myproductcatalogapp.product_previews.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fajar.myproductcatalogapp.core.ui.theme.Black10
import com.fajar.myproductcatalogapp.core.ui.theme.MyProductCatalogAppTheme
import com.fajar.myproductcatalogapp.product_previews.presentation.content.ListProductPreviewsMainContent
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.rememberDummyPagingItems
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import com.fajar.myproductcatalogapp.product_previews.presentation.R as ThisR

@Composable
internal fun ListProductPreviewsScreen(
    viewModel: ListProductPreviewsViewModel = koinViewModel(),
    openProductDetail: (Long) -> Unit = { },
) {
    val searchQuery by viewModel.searchBarQuery
        .collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingItemsOfProductPreviews
        .collectAsLazyPagingItems()

    ListProductPreviewsScreen(
        modifier = Modifier.fillMaxSize(),
        searchQueryProvider = { searchQuery },
        onSearchQueryChange = viewModel::onSearchQueryChange,
        pagingItems = pagingItems,
        onItemClicked = { openProductDetail(it.id) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ListProductPreviewsScreen(
    modifier: Modifier = Modifier,
    searchQueryProvider: () -> String = { "" },
    onSearchQueryChange: (String) -> Unit = { },
    pagingItems: LazyPagingItems<ProductPreviewItemDUI> = rememberDummyPagingItems(),
    onItemClicked: (ProductPreviewItemDUI) -> Unit = { },
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val focusManager = LocalFocusManager.current
    Scaffold(
        containerColor = Color.White,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { focusManager.clearFocus() }
            ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    scrolledContainerColor = Color.White,
                    containerColor = Color.White
                ),
                title = {},
                scrollBehavior = scrollBehavior,
                actions = {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = stringResource(ThisR.string.explore_products),
                            style = MaterialTheme.typography.headlineSmall,
                            color = Black10
                        )
                    }
                }
            )

        }
    ) { innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ListProductPreviewsMainContent(
                pagingItems = pagingItems,
                onItemClicked = {
                    onItemClicked(it)
                    focusManager.clearFocus()
                },
                searchQueryProvider = searchQueryProvider,
                onSearchQueryChange = onSearchQueryChange,
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun ListProductPreviewsScreenPreview() {
    MyProductCatalogAppTheme {
        ListProductPreviewsScreen(
            pagingItems = rememberDummyPagingItems(
                list = List(10) {
                    ProductPreviewItemDUI(
                        id = it.toLong(),
                        title = "Headset Nexus A1",
                        thumbnailImageUrl = "image.com",
                        displayPriceBeforeDiscount = "$10.00",
                        displayDiscountPercentage = "10%"
                    )
                }.toImmutableList()
            ),
            onItemClicked = { },
        )
    }
}