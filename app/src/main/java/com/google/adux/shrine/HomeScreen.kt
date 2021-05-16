package com.google.adux.shrine

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsHeight
import com.google.adux.shrine.ui.theme.ShrineTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    items: List<ItemData> = SampleItemsData,
    onAddToCart: (ItemData) -> Unit = {}
) {
    val currentScreenWidthDp = LocalConfiguration.current.screenWidthDp
    val onDesktop = currentScreenWidthDp >= Breakpoints.largeWidth

    Surface(
        modifier = modifier,
        shape = if (onDesktop) RectangleShape else MaterialTheme.shapes.large,
        elevation = if (onDesktop) 4.dp else 16.dp
    ) {
        if (!onDesktop) {
            HorizontalGrid(items, onAddToCart)
        } else {
            VerticalGrid(items, onAddToCart)
        }
    }
}

@Composable
fun FilterAction(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Icon(
            imageVector = Icons.Outlined.Tune,
            contentDescription = "Filter icon"
        )
    }
}

@Composable
fun HomeCard(
    data: ItemData,
    vertical: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (ItemData) -> Unit = {}
) {
    Column(
        modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(color = MaterialTheme.colors.primaryVariant),
            onClick = { onClick(data) }
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = data.photoResId),
                contentDescription = "Image description of photo",
                alignment = Alignment.TopCenter,
                contentScale = if (vertical) ContentScale.FillHeight else ContentScale.FillWidth,
                modifier = if (vertical) Modifier.fillMaxHeight(0.4f) else Modifier
                    .fillMaxWidth()
                    .heightIn(max = 220.dp)
            )
            Icon(
                imageVector = Icons.Outlined.AddShoppingCart,
                contentDescription = "Add to shopping cart",
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopStart)
            )
            Image(
                painter = painterResource(id = getVendorResId(data.vendor)),
                contentDescription = "Vendor logo",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 12.dp)
            )
        }
        Spacer(Modifier.height(20.dp))
        Text("${data.title}", style = MaterialTheme.typography.subtitle2)
        Spacer(Modifier.height(8.dp))
        Text("\$${data.price}", style = MaterialTheme.typography.body2)
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun HorizontalGrid(
    items: List<ItemData>,
    onAddToCart: (ItemData) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .padding(start = 16.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            val gridGutter = 16.dp
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(gridGutter)
            ) {
                var idx = 0
                while (idx < items.size) {
                    val even = idx % 3 == 0
                    if (even) {
                        Column(
                            Modifier
                                .fillMaxHeight()
                                .width(this@BoxWithConstraints.minWidth * 0.66f),
                            verticalArrangement = Arrangement.Center
                        ) {
                            HomeCard(
                                data = items[idx],
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .fillMaxWidth(0.85f),
                                onClick = { onAddToCart(it) }
                            )
                            if (items.getOrNull(idx +1) != null) {
                                Spacer(Modifier.height(32.dp))
                                HomeCard(
                                    data = items[idx + 1],
                                    modifier = Modifier.fillMaxWidth(0.85f),
                                    onClick = { onAddToCart(it) }
                                )
                            }
                        }
                    } else {
                        Column(
                            Modifier
                                .fillMaxHeight()
                                .width(this@BoxWithConstraints.minWidth * 0.66f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            HomeCard(
                                data = items[idx],
                                vertical = true,
                                modifier = Modifier.fillMaxWidth(0.8f),
                                onClick = { onAddToCart(it) }
                            )
                        }
                    }
                    idx += if (even) 2 else 1
                }

                Spacer(Modifier.width(gridGutter))
            }
        }

        FilterAction(modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(end = 4.dp),
        )
    }
}

@Composable
private fun VerticalGrid(
    items: List<ItemData>,
    onAddToCart: (ItemData) -> Unit
) {
    Row(Modifier.fillMaxSize()) {
        val itemColumns = 4

        Column(
            Modifier.weight(1f)
                .padding(horizontal = 64.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.statusBarsHeight())
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                for (i in 0 until itemColumns) {
                    val odd = i % 2 != 0
                    val basePadding = 64

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(top = if (odd) (basePadding + 72).dp else basePadding.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(48.dp)
                    ) {
                        val itemsForColumn = itemsForColumn(items, i, itemColumns)

                        itemsForColumn.forEach {
                            HomeCard(
                                data = it,
                                vertical = it.photoOrientation == PhotoOrientation.Portrait,
                                onClick = { onAddToCart(it) }
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.navigationBarsHeight())
        }
        Column(
            Modifier
                .fillMaxHeight()
                .width(64.dp)
                .padding(vertical = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.statusBarsHeight())
            FilterAction()
        }
    }
}

private fun itemsForColumn(
    items: List<ItemData>,
    idx: Int,
    maxColumns: Int
): List<ItemData> {
    val itemsForColumn = mutableListOf<ItemData>()

    for (i in items.indices step maxColumns) {
        if (items.getOrNull(i + idx) != null) {
            itemsForColumn.add(items.get(i + idx))
        }
    }

    return itemsForColumn.toList()
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Preview(showBackground = true, widthDp = 360, heightDp = 640, uiMode = UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_C)
@Composable
fun HomeScreenPreview() {
    ShrineTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeCardPreview() {
    ShrineTheme {
        Surface {
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SampleItemsData.forEach {
                    HomeCard(data = it)
                }
            }
        }
    }
}