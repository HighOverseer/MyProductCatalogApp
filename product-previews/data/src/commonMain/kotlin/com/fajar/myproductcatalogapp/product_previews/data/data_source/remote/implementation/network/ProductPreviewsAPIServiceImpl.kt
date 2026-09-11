package com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network

import com.fajar.myproductcatalogapp.core.data.network.DefaultAPIService
import com.fajar.myproductcatalogapp.product_previews.data.data_source.remote.implementation.network.responseDto.ListProductPreviewsDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal class ProductPreviewsAPIServiceImpl(
    private val httpClient: HttpClient
) : ProductPreviewsAPIService, DefaultAPIService() {
    override suspend fun getAllProductPreviews(): ListProductPreviewsDto {
        return httpClient.get {
            url {
                parameters.apply {
                    append(
                        name = LIMIT_QUERY_PARAM_KEY,
                        value = "20"
                    )
                    append(
                        name = SKIP_QUERY_PARAM_KEY,
                        value = "0"
                    )
                }
            }
        }.getBodyIfNotSuccessThrowHttpException()
    }

    companion object {
        private const val LIMIT_QUERY_PARAM_KEY = "limit"
        private const val SKIP_QUERY_PARAM_KEY = "skip"
    }
}