package com.app.presentation.component.util.responsive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun setContentPadding(screenWidth: Dp): Dp {
    return if (screenWidth < 400.dp) 8.dp else 16.dp
}

fun setTopPadding(screenWidth: Dp): Dp {
    return if (screenWidth < 400.dp) 54.dp else 38.dp
}