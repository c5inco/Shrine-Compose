package com.google.adux.shrine

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.adux.shrine.ui.theme.ShrineTheme

@Composable
fun Cart(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    maxHeight: Dp,
    maxWidth: Dp,
    onExpand: (Boolean) -> Unit = {}
) {
    val cartOpenTransition = updateTransition(
        targetState = if (expanded) CartState.Opened else CartState.Closed,
        label = "cartTransition"
    )
    val cartWidth by cartOpenTransition.animateDp(
        label = "cartWidth",
        transitionSpec = {
            when {
                CartState.Opened isTransitioningTo CartState.Closed ->
                    tween(durationMillis = 433, delayMillis = 67)
                else ->
                    tween(durationMillis = 150)
            }
        }
    ) {
        if (it == CartState.Opened) maxWidth else 200.dp
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
        if (it == CartState.Opened) maxHeight else (36 + LocalWindowInsets.current.navigationBars.bottom).dp
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
            .shadow(8.dp, CutCornerShape(topStart = cornerSize))
            .clip(CutCornerShape(topStart = cornerSize))
            .height(cartHeight)
            .width(cartWidth)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onExpand(!expanded) }
            ),
        color = MaterialTheme.colors.secondary
    ) {
        Row(
            Modifier
                .padding(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Hello")
            Text("Hello")
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
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
        }
    }
}

private enum class CartState {
    Closed,
    Opened,
}