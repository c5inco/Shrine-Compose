package com.google.adux.shrine

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.adux.shrine.ui.theme.ShrineTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier.fillMaxSize(),
        shape = MaterialTheme.shapes.large,
        elevation = 16.dp
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Outlined.Tune,
                    contentDescription = "Filter icon"
                )
            }
            BoxWithConstraints(
                Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                val gridGutter = 16.dp
                Row(
                    Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(gridGutter)
                ) {
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .width(this@BoxWithConstraints.minWidth * 0.66f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        HomeCard(
                            data = SampleItemsData[0],
                            modifier = Modifier
                                .align(Alignment.End)
                                .fillMaxWidth(0.8f)
                        )
                        Spacer(Modifier.height(40.dp))
                        HomeCard(
                            data = SampleItemsData[1],
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .width(this@BoxWithConstraints.minWidth * 0.66f - gridGutter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        HomeCard(
                            data = SampleItemsData[3],
                            modifier = Modifier.fillMaxWidth(0.66f)
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .width(this@BoxWithConstraints.minWidth * 0.66f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        HomeCard(
                            data = SampleItemsData[2],
                            modifier = Modifier
                                .align(Alignment.Start)
                                .fillMaxWidth(0.8f)
                        )
                        Spacer(Modifier.height(40.dp))
                        HomeCard(
                            data = SampleItemsData[4],
                            modifier = Modifier
                                .align(Alignment.End)
                                .fillMaxWidth(0.8f)
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .width(this@BoxWithConstraints.minWidth * 0.66f - gridGutter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        HomeCard(
                            data = SampleItemsData[5],
                            modifier = Modifier.fillMaxWidth(0.66f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeCard(
    data: ItemData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = data.photoResId),
                contentDescription = "Image description of photo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
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
        Spacer(Modifier.height(24.dp))
        Text("${data.title}", style = MaterialTheme.typography.subtitle2)
        Spacer(Modifier.height(8.dp))
        Text("\$${data.price}", style = MaterialTheme.typography.body2)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
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