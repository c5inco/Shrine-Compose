package com.google.adux.shrine

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.adux.shrine.ui.theme.ShrineTheme

@Composable
fun ExpandedCart(
    items: List<ItemData> = SampleItemsData,
    onRemoveItem: (Int) -> Unit = {},
    onCollapse: () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        var size = items.size

        Row(
            Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onCollapse() }) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Collapse cart icon"
                )
            }
            Spacer(Modifier.width(6.dp))
            Text(
                "Cart".toUpperCase(),
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(Modifier.width(12.dp))
            Text("${if (size == 0) "No" else size} item${if (size != 1) "s" else ""}".toUpperCase())
        }

        // Items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            items.forEachIndexed { idx, item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onRemoveItem(idx) }) {
                        Icon(
                            imageVector = Icons.Default.RemoveCircleOutline,
                            contentDescription = "Remove icon"
                        )
                    }
                    Spacer(Modifier.width(6.dp))
                    Column(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
                        Row(
                            Modifier.padding(vertical = 20.dp)
                        ) {
                            Image(
                                painter = painterResource(id = item.photoResId),
                                contentDescription = item.title,
                                alignment = Alignment.TopCenter,
                                contentScale = if (item.photoOrientation == PhotoOrientation.Portrait) ContentScale.FillWidth else ContentScale.FillHeight,
                                modifier = Modifier.size(80.dp)
                            )
                            Spacer(Modifier.width(20.dp))
                            Column(
                                Modifier.padding(end = 16.dp)
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${item.vendor}".toUpperCase(),
                                        style = MaterialTheme.typography.body2,
                                    )
                                    Text(
                                        text = "${formatDollar(item.price, 0)}",
                                        style = MaterialTheme.typography.body2,
                                    )
                                }
                                Text(
                                    text = "${item.title}",
                                    style = MaterialTheme.typography.subtitle2,
                                )
                            }
                        }
                    }
                }
            }
        }

        // Total
        val subtotal = items.sumBy { it.price }
        val shipping = if (items.isNotEmpty()) 10 else 0
        val tax = 0.1f

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 56.dp)
        ) {
            Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))
            Spacer(Modifier.height(24.dp))
            ConstraintLayout(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
            ) {
                val (totalText, totalValue) = createRefs()

                Text(
                    "Total".toUpperCase(),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.constrainAs(totalText) {
                        start.linkTo(parent.start)
                    }
                )
                Text(
                    "${formatDollar(subtotal * (1 + tax) + shipping)}",
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.constrainAs(totalValue) {
                        end.linkTo(parent.end)
                        baseline.linkTo(totalText.baseline)
                    }
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProvideTextStyle(
                    value = MaterialTheme.typography.body2
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text("Subtotal")
                        Text("Shipping")
                        Text("Tax")
                    }

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text("${formatDollar(subtotal)}")
                        Text("${formatDollar(shipping)}")
                        Text("${formatDollar(subtotal * tax)}")
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
        Divider(color = MaterialTheme.colors.onSecondary.copy(alpha = 0.3f))

        Spacer(Modifier.height(96.dp))
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun LongCartPreview() {
    ShrineTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colors.secondary
        ) {
            ExpandedCart()
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ShortCardPreview() {
    ShrineTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colors.secondary
        ) {
            ExpandedCart(items = SampleItemsData.subList(fromIndex = 0, toIndex = 3))
        }
    }
}