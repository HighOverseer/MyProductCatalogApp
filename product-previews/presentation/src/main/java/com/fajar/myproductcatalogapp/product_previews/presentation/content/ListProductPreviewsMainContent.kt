package com.fajar.myproductcatalogapp.product_previews.presentation.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.core.ui.components.PrimaryButton
import com.fajar.myproductcatalogapp.core.ui.components.SearchBar
import com.fajar.myproductcatalogapp.core.ui.theme.Green60
import com.fajar.myproductcatalogapp.core.ui.theme.Grey90
import com.fajar.myproductcatalogapp.core.ui.theme.Orange90
import com.fajar.myproductcatalogapp.product_previews.presentation.components.ProductPreviewItemCard
import com.fajar.myproductcatalogapp.product_previews.presentation.components.ShimmeringProductPreviewItemCard
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.appendErrorMessage
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.initialLoadErrorMessage
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isAppendLoadError
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isAppending
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isInitialLoadError
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isListEmpty
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isPrependLoadError
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isPrepending
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isRefreshing
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.prependErrorMessage
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.rememberDummyPagingItems
import com.fajar.myproductcatalogapp.product_previews.presentation.R as ThisR

@Composable
internal fun ListProductPreviewsMainContent(
    modifier: Modifier = Modifier,
    searchQueryProvider: () -> String = { "" },
    onSearchQueryChange: (String) -> Unit = { },
    pagingItems: LazyPagingItems<ProductPreviewItemDUI> = rememberDummyPagingItems(),
    onItemClicked: (ProductPreviewItemDUI) -> Unit = { },
) {
    val lazyGridState = rememberLazyGridState()
    val pullToRefreshState = rememberPullToRefreshState()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            queryProvider = searchQueryProvider,
            onQueryChange = onSearchQueryChange,
            hint = stringResource(ThisR.string.what_can_we_help_you_find),
        )

        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Grey90),
            contentAlignment = Alignment.TopCenter,
            isRefreshing = false,
            state = pullToRefreshState,
            onRefresh = { pagingItems.refresh() },
            indicator = {
                Indicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    state = pullToRefreshState,
                    isRefreshing = false,
                    containerColor = Color.White,
                    color = Color.Black
                )
            }
        ) {
            val defaultTextModifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp)

            if (pagingItems.isInitialLoadError)
                FullMaxSizeScrollableColumn {
                    Text(
                        modifier = defaultTextModifier,
                        text = pagingItems.initialLoadErrorMessage
                            ?: stringResource(R.string.an_error_occurred_please_try_again),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            else if (pagingItems.isListEmpty)
                FullMaxSizeScrollableColumn {
                    Text(
                        modifier = defaultTextModifier,
                        text = pagingItems.initialLoadErrorMessage
                            ?: stringResource(R.string.no_data_found),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
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
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (pagingItems.isRefreshing)
            items(10) { ShimmeringProductPreviewItemCard() }
        else {
            if (pagingItems.isPrepending)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    CurrentlyLoadingSection()
                }
            else if (pagingItems.isPrependLoadError)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    RetryLoadingSection(
                        errorMessage = pagingItems.prependErrorMessage
                            ?: stringResource(R.string.an_error_occurred_please_try_again),
                        onRetry = pagingItems::retry
                    )
                }


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

            if (pagingItems.isAppending)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    CurrentlyLoadingSection()
                }
            else if (pagingItems.isAppendLoadError)
                item(span = { GridItemSpan(maxLineSpan) }) {
                    RetryLoadingSection(
                        errorMessage = pagingItems.appendErrorMessage
                            ?: stringResource(R.string.an_error_occurred_please_try_again),
                        onRetry = pagingItems::retry
                    )
                }
        }


    }
}

@Composable
private fun CurrentlyLoadingSection() {
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

@Composable
private fun RetryLoadingSection(
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    onRetry: () -> Unit = { },
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 32.dp, vertical = 16.dp),
            text = errorMessage,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        PrimaryButton(
            modifier = Modifier
                .widthIn(min = 120.dp),
            text = stringResource(ThisR.string.retry),
            onClick = onRetry,
            contentPadding = PaddingValues(vertical = 0.dp)
        )
    }
}

@Composable
private fun FullMaxSizeScrollableColumn(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = { }
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        content = content
    )
}
