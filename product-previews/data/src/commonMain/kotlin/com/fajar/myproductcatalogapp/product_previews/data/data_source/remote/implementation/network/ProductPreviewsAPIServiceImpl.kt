package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network

import com.fajar.myproductcatalogapp.core.data.network.DefaultAPIService
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ListProductPreviewsDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ParametersBuilder
import io.ktor.http.appendPathSegments

internal class ProductPreviewsAPIServiceImpl(
    private val httpClient: HttpClient
) : ProductPreviewsAPIService, DefaultAPIService() {

    override suspend fun getRandomProductPreviews(
        pageSize: Int,
        pageOffset: Int
    ): ListProductPreviewsDto {
        return httpClient.get {
            url {
                parameters.appendPaginationQueryParameters(
                    pageSize = pageSize,
                    pageOffset = pageOffset
                )
            }
        }.getBodyIfNotSuccessThrowHttpException()
    }

    override suspend fun getProductPreviewsByQuery(
        query: String,
        pageSize: Int,
        pageOffset: Int
    ): ListProductPreviewsDto {
        return httpClient.get {
            url {
                appendPathSegments(SEARCH_PATH_KEY)
                parameters.apply {
                    append(SEARCH_QUERY_PARAM_KEY, query)
                    appendPaginationQueryParameters(
                        pageSize = pageSize,
                        pageOffset = pageOffset
                    )
                }
            }
        }.getBodyIfNotSuccessThrowHttpException()
    }

    private fun ParametersBuilder.appendPaginationQueryParameters(
        pageSize: Int,
        pageOffset: Int
    ) {
        @Suppress
        val limit = pageSize
        val skip = pageOffset * limit

        this.apply {
            append(
                name = LIMIT_QUERY_PARAM_KEY,
                value = limit.toString()
            )
            append(
                name = SKIP_QUERY_PARAM_KEY,
                value = skip.toString()
            )
        }
    }

    companion object {
        private const val LIMIT_QUERY_PARAM_KEY = "limit"
        private const val SKIP_QUERY_PARAM_KEY = "skip"
        private const val SEARCH_QUERY_PARAM_KEY = "q"
        private const val SEARCH_PATH_KEY = "search"
    }
}