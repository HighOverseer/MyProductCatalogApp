package com.fajar.myproductcatalogapp.product_detail.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.fajar.myproductcatalogapp.core.ui.components.BaseAsyncImage
import com.fajar.myproductcatalogapp.core.ui.theme.Green55
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import com.fajar.myproductcatalogapp.product_detail.presentation.R as ThisR

@Composable
internal fun ProductImagesCarouselSection(
    modifier: Modifier = Modifier,
    imageUrls: ImmutableList<String> = persistentListOf()
) {
    val pagerState = rememberPagerState(pageCount = { imageUrls.size })
    val imageAspectRatio = 1.652f
    Column(modifier) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageAspectRatio),
            state = pagerState
        ) { currentPage ->
            imageUrls.getOrNull(currentPage)?.let { currentImageUrl ->
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    BaseAsyncImage(
                        modifier = Modifier,
                        imageUrlOrPath = currentImageUrl,
                        contentScale = ContentScale.FillBounds,
                        contentDescription = stringResource(ThisR.string.product_photos)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imageUrls.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index)
                                Green55
                            else
                                MaterialTheme.colorScheme.outline
                        )
                )
            }
        }
    }
}