package com.fajar.myproductcatalogapp.product_detail.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fajar.myproductcatalogapp.core.domain.model.Rating
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.core.ui.theme.Black10
import com.fajar.myproductcatalogapp.core.ui.theme.Black50
import com.fajar.myproductcatalogapp.core.ui.theme.Green55
import com.fajar.myproductcatalogapp.core.ui.theme.Grey47
import com.fajar.myproductcatalogapp.core.ui.theme.MyProductCatalogAppTheme
import com.fajar.myproductcatalogapp.core.ui.theme.Orange85
import com.fajar.myproductcatalogapp.product_detail.presentation.components.ProductImagesCarouselSection
import com.fajar.myproductcatalogapp.product_detail.presentation.components.ProductRatingSection
import com.fajar.myproductcatalogapp.product_detail.presentation.components.ProductReviewItemCard
import com.fajar.myproductcatalogapp.product_detail.presentation.model.ProductDetailDUI
import com.fajar.myproductcatalogapp.product_detail.presentation.model.ProductReviewDUI
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import com.fajar.myproductcatalogapp.product_detail.presentation.R as ThisR

@Composable
internal fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    dui: ProductDetailDUI = ProductDetailDUI(),
    onNavigateUp: () -> Unit = { }
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {

        Spacer(Modifier.height(32.dp))

        IconButton(
            onClick = onNavigateUp
        ) {
            Icon(
                modifier = Modifier
                    .width(21.dp)
                    .height(20.dp),
                imageVector = ImageVector
                    .vectorResource(R.drawable.ic_arrow_left),
                contentDescription = null,
                tint = Black10
            )
        }

        Spacer(Modifier.height(16.dp))

        ProductImagesCarouselSection(imageUrls = dui.imageUrls)
        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 40.25.dp)

        ) {

            Text(
                dui.title,
                style = MaterialTheme.typography.titleLarge,
                color = Black10,
                fontSize = 24.sp
            )

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dui.displayPriceBeforeDiscount,
                    style = MaterialTheme.typography.labelMedium,
                    color = Grey47,
                    textDecoration = TextDecoration.LineThrough,
                    fontSize = 16.sp
                )

                Spacer(Modifier.width(4.dp))

                Text(
                    text = stringResource(ThisR.string.only, dui.displayPriceAfterDiscount),
                    style = MaterialTheme.typography.labelMedium,
                    color = Green55,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Orange85, RoundedCornerShape(2.dp))
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = stringResource(
                            R.string.off,
                            dui.displayDiscountPercentage
                        ),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            if (dui.overallRating != null) {
                ProductRatingSection(
                    relativeScore = dui.overallRating.normalizedRelativeScore,
                    maxScore = dui.overallRating.maxScore,
                    displayRating = dui.displayOverallRating
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                textAlign = TextAlign.Justify,
                text = dui.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Black50
            )

            Spacer(Modifier.height(24.dp))

            dui.reviews.forEach { review ->
                ProductReviewItemCard(review = review)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    MyProductCatalogAppTheme {
        ProductDetailScreen(
            dui = ProductDetailDUI(
                title = "Headset Nexus A1",
                description = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum  Lorem ipsum Lorem ipsum",
                displayPriceBeforeDiscount = "$10.0",
                displayPriceAfterDiscount = "$9.0",
                displayDiscountPercentage = "10%",
                displayOverallRating = "3.5/5",
                overallRating = Rating(3.5, 5.0),
                imageUrls = persistentListOf("image.com"),
                reviews = remember {
                    List(5) {
                        ProductReviewDUI(
                            rating = Rating(3.5, 5.0),
                            displayRating = "3.5/5",
                            comment = "Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem ipsum Lorem",
                            displayPostedDate = "12 Jan 2022",
                            reviewerName = "Andi Daud",
                            reviewerEmail = "andidaud32@gmail.com"
                        )
                    }.toImmutableList()
                }
            )
        )
    }
}