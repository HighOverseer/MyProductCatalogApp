package com.fajar.myproductcatalogapp.product_previews.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.core.ui.components.SearchBar
import com.fajar.myproductcatalogapp.core.ui.theme.Black10
import com.fajar.myproductcatalogapp.core.ui.theme.Green60
import com.fajar.myproductcatalogapp.core.ui.theme.MyProductCatalogAppTheme
import com.fajar.myproductcatalogapp.core.ui.theme.Orange90
import com.fajar.myproductcatalogapp.product_previews.presentation.components.ProductPreviewItemCard
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isInitialLoadError
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isListEmpty
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isPaginating
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isRefreshing
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.rememberDummyPagingItems
import kotlinx.collections.immutable.toImmutableList
import com.fajar.myproductcatalogapp.product_previews.presentation.R as ThisR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductPreviewsScreen(
    modifier: Modifier = Modifier,
    searchQueryProvider: () -> String = { "" },
    onSearchQueryChanged: (String) -> Unit = { },
    pagingItems: LazyPagingItems<ProductPreviewItemDUI> = rememberDummyPagingItems(),
    onItemClicked: (ProductPreviewItemDUI) -> Unit = { },
    onNavigateUp: () -> Unit = { },
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        containerColor = Color.White,
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
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
                        IconButton(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterStart),
                            onClick = onNavigateUp
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(20.dp),
                                imageVector = ImageVector
                                    .vectorResource(R.drawable.ic_arrow_left),
                                tint = Black10,
                                contentDescription = stringResource(
                                    ThisR.string.kembali
                                )
                            )
                        }
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center),
                            text = stringResource(ThisR.string.list_product_catalog),
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
                onItemClicked = onItemClicked,
                searchQueryProvider = searchQueryProvider,
                onSearchQueryChanged = onSearchQueryChanged,
            )
        }

    }
}

@Composable
internal fun ListProductPreviewsMainContent(
    modifier: Modifier = Modifier,
    searchQueryProvider: () -> String = { "" },
    onSearchQueryChanged: (String) -> Unit = { },
    pagingItems: LazyPagingItems<ProductPreviewItemDUI> = rememberDummyPagingItems(),
    onItemClicked: (ProductPreviewItemDUI) -> Unit = { },
) {
    val lazyGridState = rememberLazyGridState()
    val pullToRefreshState = rememberPullToRefreshState()
    val context = LocalContext.current

    val errorMessage = stringResource(R.string.an_error_occurred_please_try_again)
    LaunchedEffect(pagingItems) {
        val refreshState = pagingItems.loadState.refresh
        val appendState = pagingItems.loadState.append

        if (refreshState is LoadState.Error || appendState is LoadState.Error) {
            Toast.makeText(
                context,
                errorMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            queryProvider = searchQueryProvider,
            onQueryChange = onSearchQueryChanged,
            hint = stringResource(ThisR.string.what_can_we_help_you_find),
        )

        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
            isRefreshing = pagingItems.isRefreshing,
            state = pullToRefreshState,
            onRefresh = {
                pagingItems.refresh()
            },
            indicator = {
                Indicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    state = pullToRefreshState,
                    isRefreshing = pagingItems.isRefreshing,
                    containerColor = Color.White,
                    color = Color.Black
                )
            }
        ) {
            if (pagingItems.isInitialLoadError)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.an_error_occurred_please_try_again),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            else if (pagingItems.isListEmpty)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_data_found),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            else
                ListItemsSection(
                    lazyGridState = lazyGridState,
                    pagingItems = pagingItems,
                    onItemClicked = onItemClicked
                )
        }
    }
}

@Composable
private fun ListItemsSection(
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState = rememberLazyGridState(),
    pagingItems: LazyPagingItems<ProductPreviewItemDUI> = rememberDummyPagingItems(),
    onItemClicked: (ProductPreviewItemDUI) -> Unit = { },
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = lazyGridState,
        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 24.dp,
            start = 16.dp,
            end = 16.dp
        ),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { item -> item.id }
        ) { index ->
            val pagingItem = pagingItems[index]
            pagingItem?.let {
                ProductPreviewItemCard(
                    dui = pagingItem,
                    onClick = onItemClicked
                )
            }
        }

        if (pagingItems.isPaginating) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp),
                        trackColor = Orange90,
                        strokeWidth = 5.dp,
                        color = Green60,
                        gapSize = (-1).dp
                    )
                }
            }
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
                        displayPrice = "$10.00",
                        displayDiscountPercentage = "10%"
                    )
                }.toImmutableList()
            ),
            onItemClicked = { },
        )
    }
}