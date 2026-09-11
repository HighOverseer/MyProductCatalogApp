package com.fajar.myproductcatalogapp.core.common.contract

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

private object AndroidDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main.immediate
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}

internal actual fun getDispatcherProvider(): DispatcherProvider {
    return AndroidDispatcherProvider
}