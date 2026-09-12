package com.fajar.myproductcatalogapp.product_previews.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fajar.myproductcatalogapp.core.ui.R
import com.fajar.myproductcatalogapp.core.ui.components.BaseAsyncImage
import com.fajar.myproductcatalogapp.core.ui.theme.Black10
import com.fajar.myproductcatalogapp.core.ui.theme.Green55
import com.fajar.myproductcatalogapp.core.ui.theme.Grey47
import com.fajar.myproductcatalogapp.core.ui.theme.MyProductCatalogAppTheme
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
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 16.dp)
            .width(sizeMin)
    }

    Card(
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = cardModifier,
        onClick = { onClick(dui) }
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
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(16.dp))

        Row(Modifier.fillMaxWidth()) {
            Text(
                text = dui.displayPrice,
                style = MaterialTheme.typography.labelMedium,
                color = Green55
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


@Preview(showBackground = false)
@Composable
private fun ProductPreviewItemCardPreview() {
    MyProductCatalogAppTheme {
        ProductPreviewItemCard(
            dui = ProductPreviewItemDUI(
                id = 1,
                title = "Headset Nexus A1",
                thumbnailImageUrl = "image.com",
                displayPrice = "$10.00",
                displayDiscountPercentage = "10%"
            )
        )
    }
}