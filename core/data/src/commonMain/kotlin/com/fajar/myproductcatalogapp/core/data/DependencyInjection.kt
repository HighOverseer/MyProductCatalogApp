package com.fajar.myproductcatalogapp.core.data

import com.fajar.myproductcatalogapp.core.data.network.KtorClient
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

fun getCoreDataDIModules(): List<Module> {
    return listOf(
        module {
            single<HttpClient> {
                KtorClient(appConfiguration = get())
                    .client
            }
        }
    )
}