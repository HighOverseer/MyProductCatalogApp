package com.fajar.myproductcatalogapp.product_previews.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.core.ui.components.BaseAsyncImage
import com.fajar.myproductcatalogapp.core.ui.theme.Black10
import com.fajar.myproductcatalogapp.core.ui.theme.Green55
import com.fajar.myproductcatalogapp.core.ui.theme.Grey47
import com.fajar.myproductcatalogapp.core.ui.theme.MyProductCatalogAppTheme
import com.fajar.myproductcatalogapp.core.ui.theme.Orange85
import com.fajar.myproductcatalogapp.core.ui.utils.shimmeringEffect
import com.fajar.myproductcatalogapp.product_previews.presentation.model.ProductPreviewItemDUI

@Composable
internal fun ProductPreviewItemCard(
    modifier: Modifier = Modifier,
    dui: ProductPreviewItemDUI = ProductPreviewItemDUI(),
    onClick: (ProductPreviewItemDUI) -> Unit = { }
) {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val cardModifier = remember(density) {
        val imageToParentAspectRatio = 0.44f
        val screenWidth = with(density) { containerSize.width.toDp() }
        val sizeMin = screenWidth * imageToParentAspectRatio
        modifier
            .width(sizeMin)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = cardModifier,
        onClick = { onClick(dui) }
    ) {
        Column(
            Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            val imageModifier = remember {
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .aspectRatio(1.11f)
            }
            Box(
                modifier = imageModifier,
                contentAlignment = Alignment.Center
            ) {
                BaseAsyncImage(
                    modifier = Modifier.align(Alignment.Center),
                    imageUrlOrPath = dui.thumbnailImageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                dui.title,
                style = MaterialTheme.typography.titleMedium,
                color = Black10,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(Orange85, RoundedCornerShape(2.dp))
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = dui.displayDiscountPercentage + " OFF",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(Modifier.height(4.dp))

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = dui.displayPriceBeforeDiscount,
                    style = MaterialTheme.typography.labelMedium,
                    color = Grey47,
                    textDecoration = TextDecoration.LineThrough
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = dui.displayPriceAfterDiscount,
                    style = MaterialTheme.typography.labelMedium,
                    color = Green55,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = ImageVector.vectorResource(
                        R.drawable.ic_arrow_right
                    ),
                    tint = Grey47,
                    contentDescription = null
                )
            }
        }
    }

}


@Composable
fun ShimmeringProductPreviewItemCard(modifier: Modifier = Modifier) {
    val containerSize = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val cardModifier = remember(density) {
        val imageToParentAspectRatio = 0.44f
        val screenWidth = with(density) { containerSize.width.toDp() }
        val sizeMin = screenWidth * imageToParentAspectRatio
        modifier
            .width(sizeMin)
    }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = cardModifier,
    ) {
        Column(
            Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
        ) {
            val imageModifier = remember {
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .aspectRatio(1.11f)
            }
            Box(
                modifier = imageModifier,
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .shimmeringEffect()
                )
            }

            Spacer(Modifier.height(16.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .shimmeringEffect()
            )

            Spacer(Modifier.height(10.dp))

            Box(
                Modifier
                    .fillMaxWidth(0.7f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .shimmeringEffect()
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
private fun ProductPreviewItemCardPreview() {
    MyProductCatalogAppTheme {
        ProductPreviewItemCard(
            dui = ProductPreviewItemDUI(
                id = 1,
                title = "Headset Nexus A1",
                thumbnailImageUrl = "image.com",
                displayPriceBeforeDiscount = "$10.00",
                displayDiscountPercentage = "10%",
                displayPriceAfterDiscount = "$9.00"
            )
        )
    }
}

@Preview
@Composable
private fun ShimmeringProductPreviewItemCardPreview() {
    MyProductCatalogAppTheme {
        ShimmeringProductPreviewItemCard()
    }
}