package com.example.emptycompose

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emptycompose.ui.theme.EmptyComposeTheme

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun NavigationSurface() {
    EmptyComposeTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            TopAppBar(
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.logo_shrine),
                        contentDescription = "Shrine logo",
                        tint = MaterialTheme.colors.onBackground
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search icon"
                    )
                    Spacer(Modifier.width(12.dp))
                },
                elevation = 0.dp
            )
        }
    }
}