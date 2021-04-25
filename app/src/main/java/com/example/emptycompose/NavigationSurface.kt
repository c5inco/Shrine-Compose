package com.example.emptycompose

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emptycompose.ui.theme.EmptyComposeTheme

@Composable
fun NavigationSurface(
    inForeground: Boolean = false,
    activeScreen: Screens = Screens.Accessories,
    onNavigate: (Screens) -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        TopAppBar(
            title = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_shrine_logo),
                    contentDescription = "Shrine logo",
                    tint = MaterialTheme.colors.onBackground,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(24.dp))
                Text(
                    (if (inForeground) "Menu" else "Shrine").toUpperCase(),
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Normal)
                )
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
@Composable
fun NavigationSurfacePreview() {
    EmptyComposeTheme {
        var activeScreen by remember { mutableStateOf(Screens.Home) }
        NavigationSurface(
            inForeground = false,
            activeScreen = activeScreen,
            onNavigate = {
                activeScreen = it
            }
        )
    }
}

enum class Screens {
    Home,
    Accessories,
    Clothing,
    All,
    Logout
}