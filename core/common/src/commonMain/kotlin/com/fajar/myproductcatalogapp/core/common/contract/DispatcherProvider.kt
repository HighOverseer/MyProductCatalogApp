package com.fajar.myproductcatalogapp.core.common.contract

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

internal expect fun getDispatcherProvider(): DispatcherProvider