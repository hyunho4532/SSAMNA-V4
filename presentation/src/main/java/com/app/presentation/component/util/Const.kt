package com.app.presentation.component.util

import androidx.compose.ui.graphics.Color

/**
 * 땀나의 기본 스위치 색상
 */
class DefaultSwitch {
    val unfocusedContainerColor = Color.White
    val unfocusedIndicatorColor = Color(0xFFCCCACA)
    val focusedIndicatorColor = Color(0xFFADC6F8)
    val focusedContainerColor = Color.White
}

/**
 * 땀나의 기본 상수
 */
class Const {
    val pages = listOf("나이", "운동", "목소리", "걷기", "목표 기간", "스마트 워치")
    val yesORNo = listOf("네", "아니요")
    val gender = listOf("남자", "여자")
}