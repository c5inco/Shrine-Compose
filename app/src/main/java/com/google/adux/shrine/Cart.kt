package com.google.adux.shrine

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding

@Composable
fun Cart(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    maxHeight: Dp,
    maxWidth: Dp,
    onExpand: (Boolean) -> Unit = {}
) {
    //var expanded by remember { mutableStateOf(false) }

    val cornerSize by animateDpAsState(
        targetValue = if (expanded) 0.dp else 24.dp
    )
    val cartHeight by animateDpAsState(
        targetValue = if (expanded) maxHeight else (36 + LocalWindowInsets.current.navigationBars.bottom).dp,
        animationSpec =
        if (expanded)
            tween(durationMillis = 200, easing = FastOutLinearInEasing)
        else
            spring(stiffness = Spring.StiffnessMedium)
    )
    val cartWidth by animateDpAsState(
        targetValue = if (expanded) maxWidth else 200.dp,
        animationSpec =
        if (expanded)
            spring(stiffness = Spring.StiffnessMedium)
        else
            tween(durationMillis = 200, easing = FastOutLinearInEasing)
    )

    Surface(
        modifier
            .shadow(8.dp, CutCornerShape(topStart = cornerSize))
            .clip(CutCornerShape(topStart = cornerSize))
            .height(cartHeight)
            .width(cartWidth)
            .clickable {
                onExpand(!expanded)
            },
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