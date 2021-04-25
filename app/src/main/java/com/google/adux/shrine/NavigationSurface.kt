package com.google.adux.shrine

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.adux.shrine.ui.theme.EmptyComposeTheme

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
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = {
                val duration = 500
                Box(
                    Modifier
                        .width(48.dp)
                        .clickable { onToggle(!inForeground) },
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
                                    tween(durationMillis = 300, easing = LinearEasing)
                                else ->
                                    tween(durationMillis = 300)
                            }
                        }
                    ) {
                        if (it == Visibility.GONE) 24.dp else 0.dp
                    }
                    val menuIconAlpha by menuIconTransition.animateFloat(
                        label = "menuIconAlpha",
                        transitionSpec = {
                            tween(durationMillis = 300)
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
                            .size(24.dp)
                            .offset(x = logoOffset)
                    )
                }
                Spacer(Modifier.width(8.dp))
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
                                    tween(durationMillis = 300, easing = LinearEasing)
                                else ->
                                    tween(durationMillis = 300)
                            }
                        }
                    ) {
                        if (it == Visibility.GONE) 20.dp else 0.dp
                    }
                    val menuNameAlpha by menuNameTransition.animateFloat(
                        label = "menuNameAlpha",
                        transitionSpec = {
                            tween(durationMillis = 300)
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
                                    tween(durationMillis = 500)
                                else ->
                                    tween(durationMillis = 300)
                            }
                        }
                    ) {
                        if (it == Visibility.GONE) -10.dp else 0.dp
                    }
                    val shrineNameAlpha by shrineNameTransition.animateFloat(
                        label = "shrineNameAlpha",
                        transitionSpec = {
                            tween(durationMillis = 300)
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
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
            Divider(Modifier.width(56.dp))
            NavItem(Screens.Logout, Screens.Logout == activeScreen) {
                onNavigate(it)
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
        modifier = Modifier.clickable {
            onClick(screen)
        }
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
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@ExperimentalAnimationApi
@Composable
fun NavigationSurfacePreview() {
    EmptyComposeTheme {
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