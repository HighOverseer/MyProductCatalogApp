package com.fajar.myproductcatalogapp.core.ui.utils

import com.fajar.myproductcatalogapp.core.domain.model.Rating

fun Rating.toDisplay(): String {
    return "$normalizedRelativeScore/$maxScore"
}