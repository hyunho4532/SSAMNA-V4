package com.app.presentation.ui.feature.login.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.user.User
import com.app.presentation.component.util.responsive.setUpWidth
import com.app.presentation.viewmodel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AgeInfo(
    userState: State<User>,
    userViewModel: UserViewModel = hiltViewModel(),
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    val enabled = remember {
        mutableStateOf(false)
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

                Row {
                    Text(
                        text = "나이를 선택해주세요!",
                        modifier = Modifier.padding(top = 36.dp, start = 16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "회원님의 나이: ${userState.value.age.toInt()}살",
                        modifier = Modifier.padding(top = 36.dp, start = 12.dp),
                        fontSize = 14.sp
                    )
                }

                Slider(
                    modifier = Modifier
                        .width(setUpWidth())
                        .padding(top = 6.dp, start = 16.dp),
                    value = userState.value.age,
                    onValueChange = {
                        userViewModel.saveAge(it)

                        if (it > 0) {
                            enabled.value = true
                        }
                    },
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFF42B4F5),
                        activeTrackColor = Color(0xFF156ffa),
                        inactiveTrackColor = Color(0xFF5898fa)
                    ),
                    steps = 99,
                    valueRange = 0f..100f
                )
        }

        FloatingActionButton(
            onClick = {
                if (enabled.value) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                } else {

                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = if (enabled.value) Color(0xFF5c9afa) else Color.Gray
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Floating action button."
            )
        }
    }
}
