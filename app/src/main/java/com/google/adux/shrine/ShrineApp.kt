package com.google.adux.shrine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.adux.shrine.ui.theme.ShrinePink300
import com.google.adux.shrine.ui.theme.ShrineTheme

@ExperimentalAnimationApi
@Composable
fun ShrineApp() {
    val TOP_APPBAR_HEIGHT = 56
    val currentScreenWidthDp = LocalConfiguration.current.screenWidthDp
    val onDesktop = currentScreenWidthDp >= Breakpoints.largeWidth

    var activeCategory by remember { mutableStateOf(Category.All) }
    var inventory by remember { mutableStateOf(SampleItemsData.toList()) }

    var showMenu by remember { mutableStateOf(onDesktop) }
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
            if (it == ScreenState.Collapsed) (maxHeight.value - TOP_APPBAR_HEIGHT - navBarHeight).dp else (TOP_APPBAR_HEIGHT).dp
        }

        NavigationSurface(
            inForeground = showMenu,
            activeCategory = activeCategory,
            onToggle = {
                if (!onDesktop) showMenu = it
            },
            onNavigate = {
                activeCategory = it

                inventory = if (it == Category.All) {
                    SampleItemsData.toList()
                } else {
                    SampleItemsData.filter { item ->
                        item.category == activeCategory
                    }
                }
                if (!onDesktop) showMenu = false
            }
        )

        var homeScreenModifier = if (currentScreenWidthDp >= Breakpoints.largeWidth) {
            Modifier
                .align(Alignment.TopEnd)
                .width(maxWidth - 232.dp)
        } else {
            Modifier
                .height((maxHeight.value - TOP_APPBAR_HEIGHT).dp)
                .statusBarsPadding()
                .offset(y = screenOffset)
        }

        HomeScreen(
            modifier = homeScreenModifier,
            items = inventory,
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
            Box(Modifier.background(MaterialTheme.colors.surface.copy(alpha = ContentAlpha.medium)))
        }

        val cartMaxWidth = if (currentScreenWidthDp >= Breakpoints.largeWidth) 360.dp else maxWidth

        Cart(
            modifier = Modifier.align(
                if (currentScreenWidthDp >= Breakpoints.largeWidth) Alignment.TopEnd else Alignment.BottomEnd
            ),
            items = cartItems,
            expanded = showCart,
            hidden = showMenu,
            maxWidth = cartMaxWidth,
            maxHeight = maxHeight,
            onRemoveItem = { indexToRemove ->
                cartItems = cartItems.filterIndexed { idx, item ->
                    idx != indexToRemove
                }
            },
            onExpand = {
                showCart = it
            }
        )

        CheckoutButton(
            modifier = if (onDesktop) {
                Modifier
                    .align(Alignment.BottomEnd)
                    .sizeIn(maxWidth = 360.dp)
            } else {
                Modifier.align(Alignment.BottomCenter)
            },
            cartExpanded = showCart
        )
    }
}

@Preview(name = "Light theme", widthDp = 360, heightDp = 640)
@Preview(device = Devices.PIXEL_C)
@ExperimentalAnimationApi
@Composable
fun ShrineAppPreview() {
    ShrineTheme {
        ShrineApp()
    }
}

private enum class ScreenState {
    Collapsed,
    Expanded
}