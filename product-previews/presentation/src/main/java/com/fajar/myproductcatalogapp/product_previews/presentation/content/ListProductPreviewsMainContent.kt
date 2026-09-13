package com.fajar.myproductcatalogapp.product_previews.presentation.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.core.ui.components.SearchBar
import com.fajar.myproductcatalogapp.core.ui.theme.Grey90
import com.fajar.myproductcatalogapp.product_previews.presentation.components.FullMaxSizeScrollableColumn
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.initialLoadErrorMessage
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isInitialLoadError
import com.fajar.myproductcatalogapp.product_previews.presentation.utils.isListEmpty
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
                        text = pagingItems.formattedInitialLoadErrorMessage,
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

private val <T : Any> LazyPagingItems<T>.formattedInitialLoadErrorMessage: AnnotatedString
    @Composable get() {
        return buildAnnotatedString {
            val baseMessage = this@formattedInitialLoadErrorMessage
                .initialLoadErrorMessage
                ?: stringResource(R.string.an_error_occurred_please_try_again)
            append(baseMessage)

            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                appendLine()
                append(stringResource(ThisR.string.please_pull_to_refresh_again))
            }
        }
    }


