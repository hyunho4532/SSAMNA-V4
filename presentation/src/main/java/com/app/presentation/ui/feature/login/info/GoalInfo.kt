package com.app.presentation.ui.feature.login.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.domain.model.user.User
import com.app.presentation.component.row.RadioRow
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.util.Const
import kotlinx.coroutines.launch

@Composable
fun GoalInfo(
    userState: State<User>,
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    val (goalSelected, setGoalSelected) = remember {
        mutableStateOf(Const().gender[0])
    }

    val (watchSelected, setWatchSelected) = remember {
        mutableStateOf(Const().gender[0])
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
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
                modifier = Modifier.padding(top = 12.dp, start = 16.dp),
                fontSize = 16.sp
            )

            Text(
                text = "운동 중 목표 기간이 있습니까?",
                modifier = Modifier.padding(top = 32.dp, start = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            RadioRow(
                yesORNo = Const().yesORNo,
                id = 2,
                selectedOption = goalSelected,
                onOptionSelected = setGoalSelected
            )

            Spacer(
                width = 0.dp,
                height = 36.dp
            )

            Text(
                text = "현재 가지고 계신 스마트 워치가 있으신가요?",
                modifier = Modifier.padding(start = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            RadioRow(
                yesORNo = Const().yesORNo,
                id = 3,
                selectedOption = watchSelected,
                onOptionSelected = setWatchSelected
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                containerColor = Color(0xFF5c9afa)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "뒤로 가기",
                    modifier = Modifier.rotate(180f) // ← 방향으로 아이콘 회전
                )
            }

            FloatingActionButton(
                onClick = {
                    if (userState.value.targetPeriod.isNotEmpty() && userState.value.isSmartWatch.isNotEmpty()) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                containerColor = if (userState.value.targetPeriod.isNotEmpty() && userState.value.isSmartWatch.isNotEmpty()) Color(0xFF5c9afa) else Color.Gray
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "앞으로 가기!"
                )
            }
        }
    }
}