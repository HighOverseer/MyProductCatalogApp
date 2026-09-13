package com.fajar.myproductcatalogapp.product_previews.presentation.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.product_previews.presentation.components.ProductPreviewItemCard
import com.fajar.myproductcatalogapp.product_previews.presentation.components.ShimmeringProductPreviewItemCard
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.appendErrorMessage
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isAppendLoadError
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isAppending
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isPrependLoadError
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isPrepending
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isRefreshing
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.prependErrorMessage
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.rememberDummyPagingItems

@Composable
internal fun ListItemsSection(
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
