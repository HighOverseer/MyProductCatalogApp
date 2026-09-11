package com.fajar.myproductcatalogapp.core.data.network

class HttpException(
    val statusCode: Int
) : Exception()