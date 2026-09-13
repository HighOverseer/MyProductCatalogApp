package com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network

import com.fajar.myproductcatalogapp.core.data.network.DefaultAPIService
import com.fajar.myproductcatalogapp.product_detail.data.data_source.remote.implementation.network.dto.ProductDetailDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

internal class ProductDetailAPIServiceImpl(
    private val httpClient: HttpClient
) : ProductDetailAPIService, DefaultAPIService() {

    override suspend fun getProductDetail(productId: Long): ProductDetailDto {
        return httpClient.get {
            url {
                appendPathSegments(productId.toString())
            }
        }.getBodyIfNotSuccessThrowHttpException()
    }
}