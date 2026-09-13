package com.fajar.myproductcatalogapp.product_detail.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.myproductcatalogapp.core.ui.theme.Grey47
import kotlin.math.floor
import com.fajar.myproductcatalogapp.product_detail.presentation.R as ThisR

@Composable
internal fun ProductRatingSection(
    modifier: Modifier = Modifier,
    relativeScore: Double,
    maxScore: Double,
    displayRating: String,
    textSize: TextUnit = 16.sp,
    starSize: Dp = 16.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val fullStarsCount = floor(relativeScore).toInt()
        val shouldAddHalfStar = relativeScore % 1.0 != 0.0
        val emptyStarsCount = floor(
            maxScore - relativeScore
        ).toInt()

        val starComposable = remember {
            movableContentOf<Int> { starDrawableResId ->
                Image(
                    modifier = Modifier
                        .size(starSize),
                    painter = painterResource(starDrawableResId),
                    contentScale = ContentScale.Fit,
                    contentDescription = stringResource(ThisR.string.rate_star)
                )
            }
        }
        repeat(fullStarsCount) {
            starComposable(ThisR.drawable.ic_star)
        }
        if (shouldAddHalfStar) {
            starComposable(ThisR.drawable.ic_half_star)
        }
        repeat(emptyStarsCount) {
            starComposable(ThisR.drawable.ic_empty_star)
        }

        Spacer(Modifier.weight(1f))

        Text(
            text = displayRating,
            style = MaterialTheme.typography.labelMedium,
            color = Grey47,
            fontSize = textSize
        )
    }
}
