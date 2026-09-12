package com.fajar.myproductcatalogapp.product_previews.presentation.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fajar.myproductcatalogapp.core.common.contract.DispatcherProvider
import com.fajar.myproductcatalogapp.core.domain.model.Result
import com.fajar.myproductcatalogapp.core.ui.mapper.DefaultErrorMapper
import com.fajar.myproductcatalogapp.core.ui.model.UIText
import com.fajar.myproductcatalogapp.product_previews.domain.ProductPreviewsRepository
import com.fajar.myproductcatalogapp.product_previews.presentation.mapper.DUIMapper
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

internal class ProductPreviewsPagingSource(
    private val query: String,
    private val repository: ProductPreviewsRepository,
    private val duiMapper: DUIMapper,
    private val defaultErrorMapper: DefaultErrorMapper,
    private val dispatcherProvider: DispatcherProvider,
) : PagingSource<Int, ProductPreviewItemDUI>() {

    override fun getRefreshKey(state: PagingState<Int, ProductPreviewItemDUI>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { page ->
                page.prevKey?.plus(page.data.size)
                    ?: page.nextKey?.minus(page.data.size)
            }
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductPreviewItemDUI> {
        val offset = params.key ?: 0
        val size = params.loadSize

        return withContext(dispatcherProvider.default) {
            try {
                val result = repository.getProductPreviews(
                    query = query,
                    size = size,
                    offset = offset
                )

                currentCoroutineContext().ensureActive()

                when (result) {
                    is Result.Error -> {
                        val errorUIText = defaultErrorMapper.getErrorStringResource(
                            result
                        )
                        LoadResult.Error(ExceptionWithUIText(errorUIText))
                    }

                    is Result.Success -> {
                        val currentPageItems = result.data
                            .pagedList
                            .map(duiMapper::mapProductPreviewItemToDUI)

                        val isEndOfPagination =
                            currentPageItems.isEmpty() || !result.data.hasNext

                        val prevKey = if (offset == 0) {
                            null
                        } else maxOf(offset - size, 0)

                        val nextKey = if (isEndOfPagination) {
                            null
                        } else offset + currentPageItems.size

                        LoadResult.Page(
                            data = currentPageItems,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                }
            } catch (e: Exception) {
                currentCoroutineContext().ensureActive()

                LoadResult.Error(e)
            }
        }
    }
}

internal class ExceptionWithUIText(
    val text: UIText
) : Exception()