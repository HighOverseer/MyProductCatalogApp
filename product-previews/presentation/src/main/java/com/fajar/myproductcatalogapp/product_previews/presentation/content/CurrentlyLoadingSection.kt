package com.fajar.myproductcatalogapp.product_previews.presentation.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fajar.myproductcatalogapp.core.ui.theme.Green60
import com.fajar.myproductcatalogapp.core.ui.theme.Orange90

@Composable
internal fun CurrentlyLoadingSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(24.dp),
            trackColor = Orange90,
            strokeWidth = 5.dp,
            color = Green60,
            gapSize = (-1).dp
        )
    }
}
