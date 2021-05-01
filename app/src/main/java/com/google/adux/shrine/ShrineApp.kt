package com.google.adux.shrine

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
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

        var cartItems by remember { mutableStateOf(SampleItemsData.subList(fromIndex = 0, toIndex = 2)) }

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
                            tween(durationMillis = 450)
                        else ->
                            tween(durationMillis = 350)
                    }
                }
            ) {
                val navBarHeight = LocalWindowInsets.current.navigationBars.bottom
                if (it == ScreenState.Collapsed) (maxHeight.value - 56 - navBarHeight).dp else (56).dp
            }

            NavigationSurface(
                inForeground = showMenu,
                onToggle = {
                    showMenu = it
                }
            )
            HomeScreen(
                modifier = Modifier
                    .statusBarsPadding()
                    .offset(y = screenOffset),
                onAddToCart = {
                    cartItems += it
                }
            )

            // Scrim for cart transition
            AnimatedVisibility(
                modifier = Modifier.fillMaxSize(),
                visible = showCart,
                enter = fadeIn(animationSpec = tween(durationMillis = 500, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 500, easing = LinearEasing))
            ) {
                Box(Modifier.background(Color.White.copy(alpha = ContentAlpha.medium)))
            }

            Cart(
                modifier = Modifier.align(Alignment.BottomEnd),
                items = cartItems,
                expanded = showCart,
                hidden = showMenu,
                maxWidth = maxWidth,
                maxHeight = maxHeight,
                onRemoveItem = { indexToRemove ->
                    cartItems = cartItems.filterIndexed { idx, item ->
                        idx != indexToRemove
                    }
                }
            ) {
                showCart = it
            }

            val checkoutButtonTransition = updateTransition(
                targetState = if (showCart) CartState.Opened else CartState.Closed,
                label = "checkoutButtonTransition"
            )
            val checkoutButtonScale by checkoutButtonTransition.animateFloat(
                label = "checkoutButtonScale",
                transitionSpec = {
                    when {
                        CartState.Opened isTransitioningTo CartState.Closed ->
                            tween(durationMillis = 100, easing = FastOutLinearInEasing)
                        else ->
                            tween(durationMillis = 250, delayMillis = 250, easing = LinearOutSlowInEasing)
                    }
                }
            ) {
                if (it == CartState.Opened) 1f else 0.8f
            }
            val checkoutButtonAlpha by checkoutButtonTransition.animateFloat(
                label = "checkoutButtonAlpha",
                transitionSpec = {
                    when {
                        CartState.Opened isTransitioningTo CartState.Closed ->
                            tween(durationMillis = 117, easing = LinearEasing)
                        else ->
                            tween(durationMillis = 150, delayMillis = 150, easing = LinearEasing)
                    }
                }
            ) {
                if (it == CartState.Opened) 1f else 0f
            }

            CheckoutButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .scale(checkoutButtonScale)
                    .alpha(checkoutButtonAlpha)
            )

            // TODO: Ask question about actual scaleEffect, not expand for AnimatedVisibility
            // AnimatedVisibility(
            //     modifier = Modifier.align(Alignment.BottomCenter),
            //     visible = showCart,
            //     enter = fadeIn(animationSpec = tween(durationMillis = 150, delayMillis = 150, easing = LinearEasing)),
            //     exit = fadeOut(animationSpec = tween(durationMillis = 117, easing = LinearEasing))
            // ) {
            //     CheckoutButton()
            // }
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