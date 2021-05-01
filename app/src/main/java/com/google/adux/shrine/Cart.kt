package com.google.adux.shrine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.navigationBarsPadding
import com.google.adux.shrine.ui.theme.ShrineTheme

@ExperimentalAnimationApi
@Composable
fun Cart(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    hidden: Boolean = false,
    maxHeight: Dp,
    maxWidth: Dp,
    onExpand: (Boolean) -> Unit = {}
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
            (maxWidth.value - (24 + 40 * 3 + 16 * 2 + 16)).dp
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

    val collapsedCartAlpha by cartOpenTransition.animateFloat(
        label = "collapsedCartAlpha",
        transitionSpec = {
            when {
                CartState.Opened isTransitioningTo CartState.Closed ->
                    tween(durationMillis = 117, delayMillis = 117, easing = LinearEasing)
                else ->
                    tween(durationMillis = 150, easing = LinearEasing)
            }
        }
    ) {
        if (it == CartState.Opened) 0f else 1f
    }

    Surface(
        modifier
            .fillMaxWidth()
            .offset(x = cartXOffset)
            .navigationBarsHeight(cartHeight)
            .shadow(8.dp, CutCornerShape(topStart = cornerSize))
            .clip(CutCornerShape(topStart = cornerSize)),
        color = MaterialTheme.colors.secondary
    ) {
        Box {
            // Collapsed cart
            CollapsedCart(collapsedCartAlpha) {
                onExpand(true)
            }

            // Expanded cart
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(durationMillis = 150, delayMillis = 150, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 117, easing = LinearEasing))
            ) {
                ExpandedCart {
                    onExpand(false)
                }
            }
        }
    }
}

@Composable
private fun CollapsedCart(
    collapsedCartAlpha: Float,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .clickable { onClick() }
            .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 16.dp)
            .navigationBarsPadding()
            .alpha(collapsedCartAlpha),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Shopping cart icon",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        CollapsedCartItem(data = SampleItemsData[0])
        CollapsedCartItem(data = SampleItemsData[1])
    }
}

@Composable
private fun CollapsedCartItem(data: ItemData) {
    Image(
        painter = painterResource(id = data.photoResId),
        contentDescription = data.title,
        contentScale = ContentScale.FillHeight,
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}

@Composable
fun CheckoutButton(modifier: Modifier = Modifier) {
    Button(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .background(
                Brush.verticalGradient(
                colors = listOf(Color.White.copy(alpha = 0f), Color.White.copy(alpha = ContentAlpha.medium))
            ))
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
                maxWidth = maxWidth,
                maxHeight = maxHeight
            ) {
                showCart = it
            }

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