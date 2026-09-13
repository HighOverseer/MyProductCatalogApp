package com.fajar.myproductcatalogapp.product_detail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.myproductcatalogapp.core.ui.theme.Black10
import com.fajar.myproductcatalogapp.core.ui.theme.Grey65
import com.fajar.myproductcatalogapp.product_detail.presentation.model.ProductReviewDUI

@Composable
internal fun ProductReviewItemCard(
    modifier: Modifier = Modifier,
    review: ProductReviewDUI
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.White
        ),
        onClick = { }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    review.reviewerName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Black10,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    review.displayPostedDate,
                    style = MaterialTheme.typography.labelMedium,
                    color = Grey65
                )
            }


            Spacer(Modifier.height(2.dp))

            Text(
                text = review.reviewerEmail,
                style = MaterialTheme.typography.labelMedium,
                color = Black10
            )

            Spacer(
                Modifier.height(8.dp)
            )

            ProductRatingSection(
                relativeScore = review.rating.normalizedRelativeScore,
                maxScore = review.rating.maxScore,
                displayRating = review.displayRating,
                textSize = 12.sp,
                starSize = 12.dp
            )

            Spacer(
                Modifier.height(8.dp)
            )

            Text(
                text = "\"${review.comment}\"",
                style = MaterialTheme.typography.labelMedium,
                color = Black10,
                fontStyle = FontStyle.Italic
            )
        }
    }
    Spacer(Modifier.height(12.dp))
}