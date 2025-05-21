package com.app.presentation.ui.feature.analyze

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.util.responsive.setUpWidth

@Composable
fun OptimalRunning() {
    Column {
        Text(
            text = "이번 활동의 페이스 분석 - 최적 페이스!",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(
            width = setUpWidth(),
            height = 10.dp,
            isBottomBorder = true
        )

        Box(
            modifier = Modifier
                .padding(top = 12.dp)
        ) {
            Column {
                Text(
                    text = "💡 Tip:",
                    fontSize = 16.sp
                )

                Text(
                    modifier = Modifier
                        .padding(top = 12.dp),
                    text = buildAnnotatedString {
                        append("현재 최적의 페이스를 유지 중입니다! 🎯\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("계속 이 속도를 유지하면 체력 향상에 좋습니다!\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("이 페이스를 계속 유지하면 러닝 효과가 극대화됩니다.\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("운동 중간에 조금 더 강도를 올리고 싶다면, 짧게 속도를 올려보세요! 🚀\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("꾸준함이 중요해요! 좋은 페이스로 계속 달려봅시다!\n")
                    },
                    fontSize = 12.sp
                )
            }
        }
    }
}