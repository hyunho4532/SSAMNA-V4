package com.app.presentation.component.util.responsive

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun setTitleFontSize(screenWidth: Dp): TextUnit {
    return if (screenWidth < 400.dp) 20.sp else 26.sp
}

fun setSubTitleFontSize(screenWidth: Dp): TextUnit {
    return if (screenWidth < 400.dp) 18.sp else 24.sp
}

fun setFontSize(densityDpi: Int): TextUnit = when {
    densityDpi <= 160 -> 6.sp
    densityDpi <= 240 -> 8.sp
    densityDpi <= 320 -> 10.sp
    densityDpi <= 480 -> 11.sp
    densityDpi <= 560 -> 12.sp
    densityDpi <= 640 -> 14.sp
    else -> 16.sp
}