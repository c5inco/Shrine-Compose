package com.google.adux.shrine

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.adux.shrine.ui.theme.ShrineTheme

private enum class Visibility {
    VISIBLE,
    GONE
}

enum class Screens {
    Home,
    Accessories,
    Clothing,
    All,
    Logout
}

@ExperimentalAnimationApi
@Composable
fun NavigationSurface(
    inForeground: Boolean = false,
    activeCategory: Category = Category.All,
    onToggle: (Boolean) -> Unit = {},
    onNavigate: (Category) -> Unit = {}
) {
    val largeWidthBreakpoint = 1280
    val mediumWidthBreakpoint = 1024
    val smallWidthBreakpoint = 640
    val currentScreenWidthDp = LocalConfiguration.current.screenWidthDp

    if (currentScreenWidthDp < largeWidthBreakpoint) {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            Box {
                if (currentScreenWidthDp >= mediumWidthBreakpoint) {
                    InsetAwareTopAppBar(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 24.dp)
                        ,
                        title = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_shrine_logo),
                                contentDescription = "Shrine logo",
                                tint = MaterialTheme.colors.onBackground,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                "Shrine".toUpperCase(),
                                style = MaterialTheme.typography.subtitle1,
                                fontSize = 17.sp,
                            )
                            Spacer(Modifier.width(16.dp))
                            NavItemsHost(
                                true,
                                horizontalOrientation = true,
                                activeCategory = activeCategory,
                                onNavigate = { onNavigate(it) })
                        },
                        backgroundColor = MaterialTheme.colors.background,
                        actions = {
                            SearchAction()
                        },
                        elevation = 0.dp
                    )
                } else {
                    Column {
                        InsetAwareTopAppBar(
                            title = {
                                fun withLeftInset(number: Int = 0): Int {
                                    return number + if (currentScreenWidthDp > smallWidthBreakpoint) 12 else 0
                                }

                                val targetSize = withLeftInset(56).dp
                                Box(
                                    Modifier
                                        .clickable(
                                            onClick = { onToggle(!inForeground) },
                                            indication = rememberRipple(
                                                bounded = false,
                                                radius = targetSize
                                            ),
                                            interactionSource = remember { MutableInteractionSource() }
                                        )
                                        .width(targetSize)
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    // TODO: Figure out if AnimatedVisibility and animate*AsState are on different clocks

                                    val menuIconTransition = updateTransition(
                                        targetState = if (inForeground) Visibility.VISIBLE else Visibility.GONE,
                                        label = "menuIconTransition"
                                    )
                                    val logoOffset by menuIconTransition.animateDp(
                                        label = "logoOffset",
                                        transitionSpec = {
                                            when {
                                                Visibility.GONE isTransitioningTo Visibility.VISIBLE ->
                                                    tween(
                                                        durationMillis = 180,
                                                        delayMillis = 90,
                                                        easing = LinearEasing
                                                    )
                                                else ->
                                                    tween(durationMillis = 120, easing = LinearEasing)
                                            }
                                        }
                                    ) {
                                        withLeftInset(if (it == Visibility.GONE) 24 else 0).dp
                                    }
                                    val menuIconAlpha by menuIconTransition.animateFloat(
                                        label = "menuIconAlpha",
                                        transitionSpec = {
                                            when {
                                                Visibility.GONE isTransitioningTo Visibility.VISIBLE ->
                                                    tween(
                                                        durationMillis = 180,
                                                        delayMillis = 90,
                                                        easing = LinearEasing
                                                    )
                                                else ->
                                                    tween(durationMillis = 120, easing = LinearEasing)
                                            }
                                        }
                                    ) {
                                        if (it == Visibility.VISIBLE) 0f else 1f
                                    }

                                    if (!inForeground) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_menu_cut_24px),
                                            contentDescription = "Menu icon",
                                            tint = MaterialTheme.colors.onBackground,
                                            modifier = Modifier
                                                .alpha(menuIconAlpha)
                                                .offset(x = withLeftInset().dp)
                                        )
                                    }

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_shrine_logo),
                                        contentDescription = "Shrine logo",
                                        tint = MaterialTheme.colors.onBackground,
                                        modifier = Modifier
                                            .size(28.dp)
                                            .offset(x = logoOffset)
                                    )
                                }

                                Box {
                                    val menuNameTransition = updateTransition(
                                        targetState = if (inForeground) Visibility.VISIBLE else Visibility.GONE,
                                        label = "menuNameTransition"
                                    )
                                    val menuNameOffset by menuNameTransition.animateDp(
                                        label = "menuNameOffset",
                                        transitionSpec = {
                                            when {
                                                Visibility.GONE isTransitioningTo Visibility.VISIBLE ->
                                                    tween(
                                                        durationMillis = 180,
                                                        delayMillis = 90,
                                                        easing = LinearEasing
                                                    )
                                                else ->
                                                    tween(durationMillis = 120, easing = LinearEasing)
                                            }
                                        }
                                    ) {
                                        if (it == Visibility.GONE) 20.dp else 0.dp
                                    }
                                    val menuNameAlpha by menuNameTransition.animateFloat(
                                        label = "menuNameAlpha",
                                        transitionSpec = {
                                            when {
                                                Visibility.GONE isTransitioningTo Visibility.VISIBLE ->
                                                    tween(
                                                        durationMillis = 180,
                                                        delayMillis = 90,
                                                        easing = LinearEasing
                                                    )
                                                else ->
                                                    tween(durationMillis = 120, easing = LinearEasing)
                                            }
                                        }
                                    ) {
                                        if (it == Visibility.GONE) 0f else 1f
                                    }

                                    Text(
                                        "Menu".toUpperCase(),
                                        style = MaterialTheme.typography.subtitle1,
                                        fontSize = 17.sp,
                                        modifier = Modifier
                                            .offset(x = menuNameOffset)
                                            .alpha(menuNameAlpha)
                                    )

                                    val shrineNameTransition = updateTransition(
                                        targetState = if (!inForeground) Visibility.VISIBLE else Visibility.GONE,
                                        label = "shrineNameTransition"
                                    )
                                    val shrineNameOffset by shrineNameTransition.animateDp(
                                        label = "shrineNameOffset",
                                        transitionSpec = {
                                            when {
                                                Visibility.GONE isTransitioningTo Visibility.VISIBLE ->
                                                    tween(
                                                        durationMillis = 180,
                                                        delayMillis = 90,
                                                        easing = LinearEasing
                                                    )
                                                else ->
                                                    tween(durationMillis = 120, easing = LinearEasing)
                                            }
                                        }
                                    ) {
                                        if (it == Visibility.GONE) -10.dp else 0.dp
                                    }
                                    val shrineNameAlpha by shrineNameTransition.animateFloat(
                                        label = "shrineNameAlpha",
                                        transitionSpec = {
                                            when {
                                                Visibility.GONE isTransitioningTo Visibility.VISIBLE ->
                                                    tween(
                                                        durationMillis = 180,
                                                        delayMillis = 90,
                                                        easing = LinearEasing
                                                    )
                                                else ->
                                                    tween(durationMillis = 120, easing = LinearEasing)
                                            }
                                        }
                                    ) {
                                        if (it == Visibility.GONE) 0f else 1f
                                    }

                                    Text(
                                        "Shrine".toUpperCase(),
                                        style = MaterialTheme.typography.subtitle1,
                                        fontSize = 17.sp,
                                        modifier = Modifier
                                            .offset(x = shrineNameOffset)
                                            .alpha(shrineNameAlpha)
                                    )
                                }
                            },
                            backgroundColor = MaterialTheme.colors.background,
                            actions = {
                                SearchAction()
                                Spacer(Modifier.width(
                                        if (currentScreenWidthDp > smallWidthBreakpoint) 20.dp else 12.dp
                                    )
                                )
                            },
                            elevation = 0.dp
                        )

                        Spacer(Modifier.height(20.dp))
                        NavItemsHost(
                            inForeground,
                            horizontalOrientation = currentScreenWidthDp >= mediumWidthBreakpoint,
                            activeCategory = activeCategory,
                            onNavigate = { onNavigate(it) })
                    }
                }
            }
        }
    } else {
        Surface(
            Modifier.width(232.dp).fillMaxHeight(),
            color = MaterialTheme.colors.background
        ) {
            Column {
                NavItemsHost(
                    inForeground,
                    activeCategory = activeCategory,
                    onNavigate = { onNavigate(it) })
            }
        }
    }
}

@Composable
private fun SearchAction() {
    Icon(
        imageVector = Icons.Outlined.Search,
        contentDescription = "Search icon",
        tint = LocalContentColor.current.copy(alpha = ContentAlpha.high)
    )
}

@ExperimentalAnimationApi
@Composable
private fun NavItemsHost(
    inForeground: Boolean,
    horizontalOrientation: Boolean = false,
    activeCategory: Category,
    onNavigate: (Category) -> Unit
) {
    if (horizontalOrientation) {
        Row(
            Modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            NavItems(inForeground, horizontalOrientation = true, activeCategory = activeCategory, onNavigate = onNavigate)
        }
    } else {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NavItems(inForeground, activeCategory = activeCategory, onNavigate = onNavigate)
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun NavItems(
    visible: Boolean,
    horizontalOrientation: Boolean = false,
    activeCategory: Category,
    onNavigate: (Category) -> Unit
) {
    // Text("${LocalConfiguration.current.screenWidthDp}")

    var navItemModifier = Modifier.height(44.dp)

    navItemModifier = if (horizontalOrientation) {
        navItemModifier.sizeIn(minWidth = 86.dp).padding(horizontal = 20.dp)
    } else {
        navItemModifier.fillMaxWidth(0.5f)
    }

    Category.values().forEachIndexed { idx, category ->
        AnimateListItem(visible = visible, idx = idx) {
            NavItem(
                modifier = navItemModifier,
                category = category,active = category == activeCategory
            ) {
                onNavigate(it)
            }
        }
    }
    val jdx = Category.values().size

    AnimateListItem(visible = visible, idx = jdx) {
        Divider(
            if (horizontalOrientation) {
                Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight(0.5f)
                    .width(1.dp)
            } else {
                Modifier.width(56.dp)
            },
            color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
        )
    }
    AnimateListItem(visible = visible, idx = jdx + 1) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable { }
                .then(navItemModifier)
        ) {
            Text(
                "Logout".toUpperCase(),
                style = MaterialTheme.typography.subtitle1,
                color = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
            )
        }
    }
}

@Composable
fun NavItem(
    modifier: Modifier = Modifier,
    category: Category,
    active: Boolean = false,
    onClick: (Category) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick(category) }
            .then(modifier)
    ) {
        if (active) {
            Image(
                painter = painterResource(id = R.drawable.ic_tab_indicator),
                contentDescription = "Active icon",
            )
        }
        Text(
            "$category".toUpperCase(),
            style = MaterialTheme.typography.subtitle1,
            color = LocalContentColor.current.copy(alpha = if (active) ContentAlpha.high else ContentAlpha.medium),
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun AnimateListItem(visible: Boolean, idx: Int, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec =
            tween(
                durationMillis = 240,
                delayMillis = idx * 15 + 60,
                easing = LinearEasing)
        ),
        exit = fadeOut(animationSpec =
            tween(
                durationMillis = 90,
                easing = LinearEasing
            )
        )
    ) {
        content()
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Preview(showBackground = true, widthDp = 1024, heightDp = 768)
@Preview(showBackground = true, widthDp = 768, heightDp = 1024)
@ExperimentalAnimationApi
@Composable
fun NavigationSurfacePreview() {
    ShrineTheme {
        var toggle by remember { mutableStateOf(true) }
        var activeScreen by remember { mutableStateOf(Category.Accessories) }
        NavigationSurface(
            inForeground = toggle,
            activeCategory = activeScreen,
            onToggle = {
                toggle = it
            },
            onNavigate = {
                activeScreen = it
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 1024, heightDp = 768)
@ExperimentalAnimationApi
@Composable
fun NavigationSurfaceAdaptivePreview() {
    ShrineTheme {
        var toggle by remember { mutableStateOf(true) }
        var activeScreen by remember { mutableStateOf(Category.Accessories) }
        NavigationSurface(
            inForeground = toggle,
            activeCategory = activeScreen,
            onToggle = {
                toggle = it
            },
            onNavigate = {
                activeScreen = it
            }
        )
    }
}