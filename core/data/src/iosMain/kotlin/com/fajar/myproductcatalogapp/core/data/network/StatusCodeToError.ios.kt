package com.fajar.myproductcatalogapp.core.data.network

import io.ktor.client.engine.darwin.DarwinHttpRequestException

internal actual fun getUnknownHostExceptionKClass(): Any {
    return DarwinHttpRequestException::class
}