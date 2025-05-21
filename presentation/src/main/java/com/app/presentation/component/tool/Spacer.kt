package com.app.presentation.component.tool

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.presentation.component.util.bottomBorder
import com.app.presentation.component.util.bottomBorderConditional

/** 공백을 주기 위한 함수 **/
@Composable
fun Spacer(
    width: Dp,
    height: Dp?,
    isBottomBorder: Boolean = false
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height!!)
            .bottomBorderConditional(isBottomBorder) {
                bottomBorder(1.dp, MaterialTheme.colorScheme.onSurface)
            }
    )
}