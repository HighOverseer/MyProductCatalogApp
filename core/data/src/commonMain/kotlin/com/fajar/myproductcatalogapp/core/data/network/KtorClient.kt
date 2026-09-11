package com.fajar.myproductcatalogapp.core.data.network


import com.fajar.myproductcatalogapp.core.data.contract.AppConfiguration
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal class KtorClient(
    appConfiguration: AppConfiguration,
    httpClientEngine: HttpClientEngine = getHttpClientEngine(),
) {

    val client = HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            json(
                contentType = ContentType.Application.Json,
                json = Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = true
                }
            )
        }

        if (appConfiguration.isDebug) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
        }

        defaultRequest {
            url(appConfiguration.baseUrl)
        }
    }
}

internal expect fun getHttpClientEngine(): HttpClientEngine
