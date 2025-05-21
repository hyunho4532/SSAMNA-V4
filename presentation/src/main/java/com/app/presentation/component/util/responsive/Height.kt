package com.app.presentation.component.util.responsive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun setUpCardHeight(densityDpi: Int): Dp = when {
    densityDpi <= 160 -> 320.dp
    densityDpi <= 240 -> 360.dp
    densityDpi <= 320 -> 400.dp
    densityDpi <= 480 -> 440.dp
    densityDpi <= 640 -> 480.dp
    else -> 520.dp
}