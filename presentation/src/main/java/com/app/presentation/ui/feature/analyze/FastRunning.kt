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

/**
 * 빠른 조깅
 */
@Composable
fun FastRunning() {
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
                        append("속도가 빠르네요! 🚀\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("빠른 속도로 달리면 체력 소모가 커질 수 있습니다. 🏃‍♂️\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("체력이 충분하다면, 이 속도로 계속 달려보세요!\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("만약 체력이 부담된다면, 잠시 속도를 줄여보세요.\n")
                    },
                    fontSize = 12.sp
                )
            }
        }
    }
}