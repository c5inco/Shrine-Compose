package com.example.emptycompose.ui.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = CutCornerShape(4.dp),
    medium = CutCornerShape(8.dp),
    large = CutCornerShape(topStart = 24.dp)
)