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
fun UnknownRunning() {
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
                        append("현재 페이스로는 분석하기 어렵습니다!\n")
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