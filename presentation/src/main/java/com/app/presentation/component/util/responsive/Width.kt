package com.app.presentation.component.util.responsive

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min

/**
 * Jetpack compose에서 화면 크기와 관련된 값을 설정한다.
 */
@Composable
fun setUpWidth(): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return screenWidth * 0.9f
}

/**
 * 다이얼로그의 너비는 일반적으로 화면 전체 너비보다 작다.
 */
@Composable
fun setUpDialogWidth(
    cardWidth: Dp
): Dp {
    /**
     * 다이얼로그의 최대 너비는 cardWidth 파라미터로 가져온다.
     */
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return min(screenWidth * 0.8f, cardWidth)
}

/**
 * 다이얼로그의 너비 만큼 버튼 width를 맞춘다.
 */
@Composable
fun setUpButtonWidth(
    cardWidth: Dp
): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return min(screenWidth * 0.8f, cardWidth)
}

fun setUpButtonWidth(densityDpi: Int): Dp = when {
    densityDpi <= 160 -> 220.dp
    densityDpi <= 240 -> 240.dp
    densityDpi <= 320 -> 300.dp
    densityDpi <= 480 -> 320.dp
    densityDpi <= 640 -> 360.dp
    else -> 360.dp
}