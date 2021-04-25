package com.google.adux.shrine

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
            val screenTransition = updateTransition(
                targetState = if (showMenu) ScreenState.Collapsed else ScreenState.Expanded,
                label = "screenTransition"
            )
            val screenOffset by screenTransition.animateDp(
                label = "screenOffset",
                transitionSpec = {
                    when {
                        ScreenState.Expanded isTransitioningTo ScreenState.Collapsed ->
                            tween(durationMillis = 300)
                        else ->
                            spring()
                    }
                }
            ) {
                if (it == ScreenState.Collapsed) (maxHeight.value - 56).dp else 56.dp
            }

            NavigationSurface(
                inForeground = showMenu,
                onToggle = {
                    showMenu = it
                }
            )
            HomeScreen(modifier = Modifier.offset(y = screenOffset))
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@ExperimentalAnimationApi
@Composable
fun ShrineAppPreview() {
    ShrineApp()
}

private enum class ScreenState {
    Collapsed,
    Expanded
}