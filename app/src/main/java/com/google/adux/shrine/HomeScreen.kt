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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.adux.shrine.ui.theme.ShrineTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    items: List<ItemData> = SampleItemsData,
    onAddToCart: (ItemData) -> Unit = {}
) {
    Surface(
        modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = 16.dp
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

            IconButton(
                modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 4.dp),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filter icon"
                )
            }
        }
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
                modifier = if (vertical) Modifier.fillMaxHeight(0.4f) else Modifier.fillMaxWidth().heightIn(max = 220.dp)
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

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Preview(showBackground = true, widthDp = 360, heightDp = 640, uiMode = UI_MODE_NIGHT_YES)
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