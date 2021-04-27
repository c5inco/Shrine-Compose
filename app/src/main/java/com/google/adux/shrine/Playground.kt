package com.google.adux.shrine

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true, device = Devices.PIXEL_2, showSystemUi = true)
@Composable
fun PlaygroundPreview() {
    MaterialTheme {
        Column(
            Modifier.padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null
                )
                Spacer(Modifier.width(8.dp))
                Text("Default button")
            }
            CustomButton()
            CustomButton()
            CustomButton()
        }
    }
}

@Composable
private fun CustomButton() {
    Row(
        Modifier
            .clickable { }
            .wrapContentHeight()
            .width(200.dp)
            .background(
                color = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(ButtonDefaults.ContentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
        Icon(
            imageVector = Icons.Default.Face,
            contentDescription = null,
            tint = contentColor
        )
        Spacer(Modifier.width(8.dp))
        Text(
            "Custom button",
            style = MaterialTheme.typography.button,
            color = contentColor
        )
    }
}
