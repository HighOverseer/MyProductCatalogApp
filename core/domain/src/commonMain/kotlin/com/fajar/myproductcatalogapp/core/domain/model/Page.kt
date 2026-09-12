package com.fajar.myproductcatalogapp.core.domain.model

/**
 * Represents each individual Paged Item of Pagination
 */
data class Page<T>(
    val data: List<T>,
    val hasNext: Boolean
)