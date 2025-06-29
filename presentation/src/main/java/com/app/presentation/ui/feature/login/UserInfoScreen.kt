package com.app.presentation.ui.feature.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.domain.model.user.User
import com.app.presentation.ui.feature.login.info.AgeInfo
import com.app.presentation.ui.feature.login.info.ExerciseInfo
import com.app.presentation.ui.feature.login.info.GoalInfo
import com.app.presentation.ui.feature.login.info.VoiceInfo
import com.app.presentation.viewmodel.TTSViewModel
import com.app.presentation.viewmodel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun UserInfoScreen(
    navController: NavController,
    user: User,
    userViewModel: UserViewModel = hiltViewModel(),
    ttsViewModel: TTSViewModel = hiltViewModel()
) {

    /**
     * Pager 기능을 구현하기 위해 아래 변수를 선언
     */
    val pagerState = rememberPagerState(
        pageCount = {
            6
        }
    )

    /**
     * 상태 관리 (userState: 사용자, voice: 목소리 (사운드))
     */
    val userState = userViewModel.user.collectAsState()
    val voice = ttsViewModel.voice.collectAsState()

    LaunchedEffect(user) {
        if (user.email.isNotEmpty()) {
            userViewModel.mergeAuthStateIntoUserState(user = user)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize(),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> AgeInfo(
                    userState = userState,
                    pagerState = pagerState
                )
                1 -> ExerciseInfo(
                    userState = userState,
                    pagerState = pagerState
                )
                2 -> VoiceInfo(
                    userState = userState,
                    pagerState = pagerState
                )
                3 -> GoalInfo(
                    userState = userState,
                    pagerState = pagerState
                )
                4 -> ReportScreen(
                    userState = userState.value,
                    voiceState = voice.value,
                    pagerState = pagerState
                )
            }
        }
    }
}