package com.fajar.myproductcatalogapp.product_previews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.fajar.myproductcatalogapp.core.common.contract.DispatcherProvider
import com.fajar.myproductcatalogapp.core.ui.mapper.DefaultErrorMapper
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewsRepository
import com.fajar.myproductcatalogapp.product_previews.presentation.mapper.DUIMapper
import com.fajar.myproductcatalogapp.product_previews.presentation.pagination.ProductPreviewsPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.seconds

internal class ListProductPreviewsViewModel(
    private val repository: ProductPreviewsRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val duiMapper: DUIMapper,
    private val defaultErrorMapper: DefaultErrorMapper,
) : ViewModel() {

    private val _searchBarQuery = MutableStateFlow("")
    val searchBarQuery = _searchBarQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val pagingItemsOfProductPreviews = _searchBarQuery
        .debounce(SEARCH_QUERY_DEBOUNCE_TIMEOUT)
        .flatMapLatest { currentQuery ->
            Pager(
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    initialLoadSize = INITIAL_LOAD_SIZE,
                    prefetchDistance = PREFETCH_DISTANCE,
                    maxSize = MAX_SIZE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    ProductPreviewsPagingSource(
                        query = currentQuery,
                        repository = repository,
                        duiMapper = duiMapper,
                        defaultErrorMapper = defaultErrorMapper,
                        dispatcherProvider = dispatcherProvider
                    )
                }
            ).flow
        }
        .flowOn(dispatcherProvider.default)
        .cachedIn(viewModelScope)

    fun onSearchQueryChange(newQuery: String) {
        _searchBarQuery.update { newQuery }
    }

    companion object {
        private val SEARCH_QUERY_DEBOUNCE_TIMEOUT = 0.5.seconds
        private const val PAGE_SIZE = 10
        private const val MAX_SIZE = 10 * PAGE_SIZE
        private const val INITIAL_LOAD_SIZE = 20
        private const val PREFETCH_DISTANCE = 4
    }
}