package com.fajar.myproductcatalogapp.product_previews.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fajar.myproductcatalogapp.product_previews.presentation.pagination.ExceptionWithUIText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf

/**
 * Doesn't need to place this file in core ui module Yet.
 */


@Composable
internal fun <T : Any> rememberDummyPagingItems(
    list: ImmutableList<T> = persistentListOf()
): LazyPagingItems<T> {
    return remember {
        flowOf(PagingData.from(list))
    }.collectAsLazyPagingItems()
}

internal val <T : Any> LazyPagingItems<T>.isInitialLoadError
    get() = loadState.refresh is LoadState.Error
internal val <T : Any> LazyPagingItems<T>.isAppendLoadError
    get() = loadState.append is LoadState.Error

internal val <T : Any> LazyPagingItems<T>.isPrependLoadError
    get() = loadState.prepend is LoadState.Error
internal val <T : Any> LazyPagingItems<T>.isInitialLoadFinished
    get() = loadState.refresh is LoadState.NotLoading
internal val <T : Any> LazyPagingItems<T>.isListEmpty
    get() = itemCount == 0 && isInitialLoadFinished
internal val <T : Any> LazyPagingItems<T>.isRefreshing
    get() = loadState.refresh is LoadState.Loading
internal val <T : Any> LazyPagingItems<T>.isAppending
    get() = loadState.append is LoadState.Loading

internal val <T : Any> LazyPagingItems<T>.isPrepending
    get() = loadState.prepend is LoadState.Loading

internal val <T : Any> LazyPagingItems<T>.initialLoadErrorMessage
    @Composable get() = (loadState.refresh as? LoadState.Error)
        ?.error?.let { error ->
            if (error is ExceptionWithUIText) {
                error.text.getValue()
            } else error.message
        }

internal val <T : Any> LazyPagingItems<T>.appendErrorMessage
    @Composable get() = (loadState.append as? LoadState.Error)
        ?.error?.let { error ->
            if (error is ExceptionWithUIText) {
                error.text.getValue()
            } else error.message
        }

internal val <T : Any> LazyPagingItems<T>.prependErrorMessage
    @Composable get() = (loadState.prepend as? LoadState.Error)
        ?.error?.let { error ->
            if (error is ExceptionWithUIText) {
                error.text.getValue()
            } else error.message
        }