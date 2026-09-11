package com.fajar.myproductcatalogapp.core.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp

internal actual fun getHttpClientEngine(): HttpClientEngine {
    return OkHttp.create()
}