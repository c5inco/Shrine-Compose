package com.google.adux.shrine

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.adux.shrine.ui.theme.ShrineTheme

@ExperimentalAnimationApi
@Composable
fun ShrineApp() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = useDarkIcons)
    }

    ShrineTheme {
        var showMenu by remember { mutableStateOf(false) }
        var showCart by remember { mutableStateOf(false) }

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
                val navBarHeight = LocalWindowInsets.current.navigationBars.bottom
                if (it == ScreenState.Collapsed) (maxHeight.value - 56 - navBarHeight).dp else (56).dp
            }
            val screenAlpha by animateFloatAsState(targetValue = if (showCart) 0f else 1f)

            NavigationSurface(
                inForeground = showMenu,
                onToggle = {
                    showMenu = it
                }
            )
            HomeScreen(modifier = Modifier
                .statusBarsPadding()
                .offset(y = screenOffset)
                .alpha(screenAlpha)
            )

            Cart(
                modifier = Modifier.align(Alignment.BottomEnd),
                expanded = showCart,
                maxWidth = maxWidth,
                maxHeight = maxHeight
            ) {
                showCart = it
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4, showSystemUi = true)
@ExperimentalAnimationApi
@Composable
fun ShrineAppPreview() {
    ShrineApp()
}

private enum class ScreenState {
    Collapsed,
    Expanded
}