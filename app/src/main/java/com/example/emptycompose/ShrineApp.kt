package com.example.emptycompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emptycompose.ui.theme.EmptyComposeTheme

@Composable
fun ShrineApp() {
    EmptyComposeTheme {
        Box {
            NavigationSurface()
            HomeScreen(modifier = Modifier.offset(y = 56.dp))
        }
    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun ShrineAppPreview() {
    ShrineApp()
}