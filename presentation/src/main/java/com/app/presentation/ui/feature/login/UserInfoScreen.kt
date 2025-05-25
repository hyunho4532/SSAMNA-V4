package com.app.presentation.ui.feature.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.domain.model.user.User
import com.app.presentation.component.util.Const
import com.app.presentation.ui.feature.login.info.AgeInfo
import com.app.presentation.ui.feature.login.info.ExerciseInfo
import com.app.presentation.ui.feature.login.info.GoalInfo
import com.app.presentation.ui.feature.login.info.VoiceInfo
import com.app.presentation.viewmodel.TTSViewModel
import com.app.presentation.viewmodel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

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
    val pagerState = rememberPagerState()

    val userState = userViewModel.user.collectAsState()

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
            count = Const().pages.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
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
                    userState = userState
                )
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }
}