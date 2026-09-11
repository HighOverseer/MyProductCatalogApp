package com.fajar.myproductcatalogapp.core.common.contract

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


private object IOSDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.Default.limitedParallelism(4)
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}

internal actual fun getDispatcherProvider(): DispatcherProvider {
    return IOSDispatcherProvider
}