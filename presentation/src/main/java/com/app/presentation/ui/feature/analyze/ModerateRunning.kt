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
fun ModerateRunning() {
    Column {
        Text(
            text = "이번 활동의 페이스 분석 - 꿀팁",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Spacer(
            width = setUpWidth(),
            height = 10.dp,
            isBottomBorder = true
        )

        Box(modifier = Modifier.padding(top = 12.dp)) {
            Column {
                Text(
                    text = "💡 Tip:",
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = buildAnnotatedString {
                        append("적당한 속도로 달리고 있어요! 🎯\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("체력이 부담스럽지 않다면, 이 속도를 유지해주세요.\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("조금 더 강도를 높이고 싶다면, 속도를 살짝 올려보세요!\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("계속 좋은 페이스로 달리세요! 🏃‍♂️")
                    },
                    fontSize = 12.sp
                )
            }
        }
    }
}