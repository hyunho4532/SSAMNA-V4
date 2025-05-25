package com.app.presentation.ui.feature.login.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AgeInfo() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "회원님의 정보가 필요해요!",
            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "정보에 맞게 운동 정보를 제공해드립니다!",
            modifier = Modifier.padding(top = 48.dp, start = 16.dp),
            fontSize = 16.sp
        )
    }
}