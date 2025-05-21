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
 * 페이스가 낮을 때
 */
@Composable
fun SlowRunning() {
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
                        append("천천히라도 계속 움직이는 것이 핵심이에요\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("몸이 익숙해질 수 있도록 편한 속도로 유지해보세요.\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                        append("페이스를 올리고 싶다면, 30초만 가볍게 속도를 올려보는 것도 좋아요!\n")
                        withStyle(SpanStyle(fontSize = 8.sp)) {
                            append("\n")
                        }
                    },
                    fontSize = 12.sp
                )
            }
        }
    }
}
