package com.app.presentation.component.theme

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun SsamnaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primaryContainer = Color.Gray,
            surface = Color.Gray,
            background = Color.DarkGray,
            surfaceVariant = Color.Gray,
            surfaceTint = Color.Gray
        )
    } else {
        lightColorScheme(
            primaryContainer = Color.White,
            surface = Color.White,
            background = Color.White,
            surfaceVariant = Color.White,
            surfaceTint = Color.White // THIS ONE
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
