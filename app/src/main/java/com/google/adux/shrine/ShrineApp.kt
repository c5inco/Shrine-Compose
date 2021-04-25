package com.google.adux.shrine

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.adux.shrine.ui.theme.EmptyComposeTheme

@ExperimentalAnimationApi
@Composable
fun ShrineApp() {
    EmptyComposeTheme {
        var showMenu by remember { mutableStateOf(false) }

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val screenOffsetState by animateDpAsState(targetValue = if (showMenu) (maxHeight.value - 56).dp else 56.dp)

            NavigationSurface(
                inForeground = showMenu,
                onToggle = {
                    showMenu = it
                }
            )
            HomeScreen(modifier = Modifier.offset(y = screenOffsetState))
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@ExperimentalAnimationApi
@Composable
fun ShrineAppPreview() {
    ShrineApp()
}