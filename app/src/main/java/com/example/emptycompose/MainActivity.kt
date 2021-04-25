package com.example.emptycompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.emptycompose.ui.theme.EmptyComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmptyComposeTheme {
                HomeScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EmptyComposeTheme {
        Button(enabled = false, onClick = { /*TODO*/ }) {
            Text("hello", color = MaterialTheme.colors.primary)
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "face",
                tint = MaterialTheme.colors.primary
            )
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "face",
            )
        }
    }
}