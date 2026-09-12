package com.fajar.myproductcatalogapp.core.ui.di

import com.fajar.myproductcatalogapp.core.ui.mapper.DefaultErrorMapper
import org.koin.core.module.Module
import org.koin.dsl.module

fun getCoreUIDIModules(): List<Module> {
    return listOf(
        module {
            single<DefaultErrorMapper> { DefaultErrorMapper() }
        }
    )
}