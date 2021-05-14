package com.google.adux.shrine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import com.google.adux.shrine.ui.theme.ShrineTheme
import kotlin.math.min

@ExperimentalAnimationApi
@Composable
fun Cart(
    modifier: Modifier = Modifier,
    items: List<ItemData> = SampleItemsData,
    expanded: Boolean = false,
    hidden: Boolean = false,
    maxHeight: Dp,
    maxWidth: Dp,
    onRemoveItem: (Int) -> Unit = {},
    onExpand: (Boolean) -> Unit = {},
) {
    val cartOpenTransition = updateTransition(
        targetState = if (hidden) CartState.Hidden else if (expanded) CartState.Opened else CartState.Closed,
        label = "cartTransition"
    )

    val cartXOffset by cartOpenTransition.animateDp(
        label = "cartXOffset",
        transitionSpec = {
            when {
                CartState.Closed isTransitioningTo CartState.Hidden ->
                    tween(durationMillis = 450)
                CartState.Hidden isTransitioningTo CartState.Closed ->
                    tween(durationMillis = 450)
                CartState.Opened isTransitioningTo CartState.Closed ->
                    tween(durationMillis = 433, delayMillis = 67)
                else ->
                    tween(durationMillis = 150)
            }
        }
    ) {
        if (it == CartState.Hidden) {
            maxWidth
        }
        else if (it == CartState.Opened) {
            0.dp
        } else {
            val size = min(3, items.size)
            var width = 24 + 40 * (size + 1) + 16 * size + 16
            if (items.size > 3) width += 24 + 16
            (maxWidth.value - (width)).dp
        }
    }

    val cartHeight by cartOpenTransition.animateDp(
        label = "cartHeight",
        transitionSpec = {
            when {
                CartState.Opened isTransitioningTo CartState.Closed ->
                    tween(durationMillis = 283)
                else ->
                    tween(durationMillis = 500)
            }
        }
    ) {
        if (it == CartState.Opened) maxHeight else (40 + 8 + 8).dp
    }

    val cornerSize by cartOpenTransition.animateDp(
        label = "cartCornerSize",
        transitionSpec = {
            when {
                CartState.Opened isTransitioningTo CartState.Closed ->
                    tween(durationMillis = 433, delayMillis = 67)
                else ->
                    tween(durationMillis = 150)
            }
        }
    ) {
        if (it == CartState.Opened) 0.dp else 24.dp
    }

    Surface(
        modifier
            .fillMaxWidth()
            .offset(x = cartXOffset)
            .navigationBarsHeight(cartHeight)
            .shadow(8.dp, CutCornerShape(topStart = cornerSize))
            .clip(CutCornerShape(topStart = cornerSize)),
        elevation = if (isSystemInDarkTheme()) 24.dp else 0.dp
    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colors.secondary.copy(alpha = if (isSystemInDarkTheme()) 0.08f else 1f))
        ) {
            // Collapsed cart
            AnimatedVisibility(
                visible = !expanded,
                enter = fadeIn(animationSpec = tween(durationMillis = 150, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 117, delayMillis = 117, easing = LinearEasing))
            ) {
                CollapsedCart(
                    items = items,
                ) {
                    onExpand(true)
                }
            }

            // Expanded cart
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(durationMillis = 150, delayMillis = 150, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 117, easing = LinearEasing))
            ) {
                ExpandedCart(
                    items = items,
                    onRemoveItem = {
                        onRemoveItem(it)
                    }
                ) {
                    onExpand(false)
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun CollapsedCart(
    items: List<ItemData> = SampleItemsData.subList(fromIndex = 0, toIndex = 3),
    onClick: () -> Unit
) {
    Row(
        Modifier
            .clickable { onClick() }
            .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping cart icon",
            )
        }
        for (i in 0 until min(3, items.size)) {
            key(i) {
                CollapsedCartItem(data = items[i])
            }
        }
        if (items.size > 3) {
            Box(
                Modifier.size(width = 24.dp, height = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "+${items.size - 3}",
                    style = MaterialTheme.typography.subtitle2,
                )
            }
        }
    }
}

@Composable
private fun CollapsedCartItem(data: ItemData) {
    var currentState = remember { MutableTransitionState(CollapsedCartItem.Initial) }
    currentState.targetState = CollapsedCartItem.Added
    val transition = updateTransition(currentState)

    val iconScale by transition.animateFloat(
        label = "iconScale",
        transitionSpec = {
            tween(durationMillis = 250, delayMillis = 100, easing = LinearOutSlowInEasing)
        }
    ) {
        if (it == CollapsedCartItem.Added) 1f else 0.2f
    }
    val iconAlpha by transition.animateFloat(
        label = "iconAlpha",
        transitionSpec = {
            tween(durationMillis = 150, easing = LinearEasing)
        }
    ) {
        if (it == CollapsedCartItem.Added) 1f else 0f
    }

    Image(
        painter = painterResource(id = data.photoResId),
        contentDescription = data.title,
        alignment = Alignment.TopCenter,
        contentScale = if (data.photoOrientation == PhotoOrientation.Portrait) ContentScale.FillWidth else ContentScale.FillHeight,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .graphicsLayer {
                alpha = iconAlpha
                scaleX = iconScale
                scaleY = iconScale
            }
    )
}

private enum class CollapsedCartItem {
    Initial,
    Added
}

@Composable
fun CheckoutButton(
    modifier: Modifier = Modifier,
    cartExpanded: Boolean = false
) {
    val checkoutButtonTransition = updateTransition(
        targetState = if (cartExpanded) CartState.Opened else CartState.Closed,
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
    val checkoutButtonScrimAlpha by checkoutButtonTransition.animateFloat(
        label = "checkoutButtonScrimAlpha",
        transitionSpec = {
            tween(durationMillis = 150, delayMillis = 400)
        }
    ) {
        if (it == CartState.Opened) ContentAlpha.medium else 0f
    }

    Button(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .graphicsLayer {
                alpha = checkoutButtonAlpha
                scaleX = checkoutButtonScale
                scaleY = checkoutButtonScale
            }
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colors.surface.copy(alpha = checkoutButtonScrimAlpha)
                    )
                )
            )
            .padding(24.dp)
            .navigationBarsPadding()
            .fillMaxWidth(),
        onClick = { }
    ) {
        Text("Proceed to checkout".toUpperCase())
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@ExperimentalAnimationApi
@Composable
fun CartPreview() {
    var showCart by remember { mutableStateOf(false) }

    ShrineTheme {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            Cart(
                modifier = Modifier.align(Alignment.BottomEnd),
                expanded = showCart,
                maxHeight = maxHeight,
                maxWidth = maxWidth,
                onExpand = {
                    showCart = it
                },
                items = SampleItemsData.subList(fromIndex = 0, toIndex = 3)
            )

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = showCart,
                enter = fadeIn(animationSpec = tween(durationMillis = 150, delayMillis = 150, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 117, easing = LinearEasing))
            ) {
                CheckoutButton()
            }
        }
    }
}

enum class CartState {
    Closed,
    Opened,
    Hidden
}