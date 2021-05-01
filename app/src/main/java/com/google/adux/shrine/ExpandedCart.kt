package com.google.adux.shrine

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.adux.shrine.ui.theme.ShrineTheme

@Composable
fun ExpandedCart(items: List<ItemData> = SampleItemsData) {
    Column(
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        var size = items.size

        Row(
            Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) {
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
            Text("$size item${if (size != 1) "s" else ""}".toUpperCase())
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { idx, item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.RemoveCircleOutline,
                        contentDescription = "Remove icon"
                    )
                    Spacer(Modifier.width(16.dp))
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
                                contentScale = ContentScale.FillHeight,
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
                                        text = "${item.vendor}",
                                        style = MaterialTheme.typography.body2,
                                    )
                                    Text(
                                        text = "$${item.price}",
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
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun ExpandedCartPreview() {
    ShrineTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colors.secondary
        ) {
            ExpandedCart()
        }
    }
}