package com.example.emptycompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Color(0xfffedbd0),
    primaryVariant = Color(0xfffbb8ac),
    secondary = Color(0xfffeeae6),
    background = Color(0xfffedbd0),
    surface = Color(0xfffffbfa),
    error = Color(0xffc5032b),
    onPrimary = Maroon5,
    onSecondary = Maroon5,
    onBackground = Maroon5,
    onSurface = Maroon5,
    onError = Color(0xfffffbfa)
)

@Composable
fun EmptyComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Preview(widthDp = 360)
@Composable
fun ThemePreview() {
    EmptyComposeTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddShoppingCart,
                    contentDescription = "Add to card icon"
                )
                Spacer(Modifier.width(16.dp))
                Text("Add to cart".toUpperCase())
            }
            Card(
                Modifier.size(width = 200.dp, height = 300.dp)
            ) {
                Column(
                    Modifier.padding(16.dp)
                ) {
                    Text("This is a card")
                }
            }
        }
    }
}