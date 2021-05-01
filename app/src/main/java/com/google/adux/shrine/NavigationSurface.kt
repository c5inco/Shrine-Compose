package com.google.adux.shrine

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.adux.shrine.ui.theme.ShrineTheme
import kotlinx.coroutines.delay

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
    activeScreen: Screens = Screens.Accessories,
    onToggle: (Boolean) -> Unit = {},
    onNavigate: (Screens) -> Unit = {}
) {
    Surface(
        Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            InsetAwareTopAppBar(
                title = {
                    Box(
                        Modifier
                            .width(56.dp)
                            .fillMaxHeight()
                            .clickable(
                                onClick = { onToggle(!inForeground) },
                                indication = rememberRipple(bounded = false, radius = 56.dp),
                                interactionSource = remember { MutableInteractionSource() }
                            ),
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
                                        tween(durationMillis = 180, delayMillis = 90, easing = LinearEasing)
                                    else ->
                                        tween(durationMillis = 120, easing = LinearEasing)
                                }
                            }
                        ) {
                            if (it == Visibility.GONE) 24.dp else 0.dp
                        }
                        val menuIconAlpha by menuIconTransition.animateFloat(
                            label = "menuIconAlpha",
                            transitionSpec = {
                                when {
                                    Visibility.GONE isTransitioningTo Visibility.VISIBLE ->
                                        tween(durationMillis = 180, delayMillis = 90, easing = LinearEasing)
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
                                modifier = Modifier.alpha(menuIconAlpha)
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
                                        tween(durationMillis = 180, delayMillis = 90, easing = LinearEasing)
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
                                        tween(durationMillis = 180, delayMillis = 90, easing = LinearEasing)
                                    else ->
                                        tween(durationMillis = 120, easing = LinearEasing)
                                }
                            }
                        ) {
                            if (it == Visibility.GONE) 0f else 1f
                        }

                        Text(
                            "Menu".toUpperCase(),
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Normal),
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
                                        tween(durationMillis = 180, delayMillis = 90, easing = LinearEasing)
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
                                        tween(durationMillis = 180, delayMillis = 90, easing = LinearEasing)
                                    else ->
                                        tween(durationMillis = 120, easing = LinearEasing)
                                }
                            }
                        ) {
                            if (it == Visibility.GONE) 0f else 1f
                        }

                        Text(
                            "Shrine".toUpperCase(),
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Normal),
                            modifier = Modifier
                                .offset(x = shrineNameOffset)
                                .alpha(shrineNameAlpha)
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search icon"
                    )
                    Spacer(Modifier.width(12.dp))
                },
                elevation = 0.dp
            )

            if (inForeground) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(Modifier.height(20.dp))
                    NavItem(Screens.All, Screens.All == activeScreen) {
                        onNavigate(it)
                    }
                    NavItem(Screens.Accessories, Screens.Accessories == activeScreen) {
                        onNavigate(it)
                    }
                    NavItem(Screens.Clothing, Screens.Clothing == activeScreen) {
                        onNavigate(it)
                    }
                    NavItem(Screens.Home, Screens.Home == activeScreen) {
                        onNavigate(it)
                    }
                    Divider(
                        Modifier.width(56.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                    )
                    NavItem(Screens.Logout, Screens.Logout == activeScreen) {
                        onNavigate(it)
                    }
                }
            }
        }
    }
}

@Composable
fun NavItem(
    screen: Screens,
    active: Boolean = false,
    onClick: (Screens) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick(screen) }
            .fillMaxWidth(0.5f)
            .height(44.dp)
    ) {
        if (active) {
            Image(
                painter = painterResource(id = R.drawable.ic_tab_indicator),
                contentDescription = "Active icon",
            )
        }
        Text(
            "$screen".toUpperCase(),
            style = MaterialTheme.typography.subtitle1,
            color = LocalContentColor.current.copy(alpha = if (active) ContentAlpha.high else ContentAlpha.medium),
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@ExperimentalAnimationApi
@Composable
fun NavigationSurfacePreview() {
    ShrineTheme {
        var toggle by remember { mutableStateOf(true) }
        var activeScreen by remember { mutableStateOf(Screens.Home) }
        NavigationSurface(
            inForeground = toggle,
            activeScreen = activeScreen,
            onToggle = {
                toggle = it
            },
            onNavigate = {
                activeScreen = it
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@ExperimentalAnimationApi
@Composable
fun NavigationSurfaceDarkPreview() {
    ShrineTheme(darkTheme = true) {
        var toggle by remember { mutableStateOf(true) }
        var activeScreen by remember { mutableStateOf(Screens.Home) }
        NavigationSurface(
            inForeground = toggle,
            activeScreen = activeScreen,
            onToggle = {
                toggle = it
            },
            onNavigate = {
                activeScreen = it
            }
        )
    }
}