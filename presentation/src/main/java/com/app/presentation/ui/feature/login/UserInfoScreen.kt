package com.app.presentation.ui.feature.login

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.domain.model.enum.ButtonType
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.user.User
import com.app.presentation.R
import com.app.presentation.component.row.RadioRow
import com.app.presentation.component.tool.CustomButton
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.util.Const
import com.app.presentation.component.util.responsive.setFontSize
import com.app.presentation.component.util.getDPI
import com.app.presentation.component.util.responsive.setUpWidth
import com.app.presentation.ui.feature.login.info.AgeInfo
import com.app.presentation.viewmodel.TTSViewModel
import com.app.presentation.viewmodel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun UserInfoScreen(
    navController: NavController,
    user: User,
    userViewModel: UserViewModel = hiltViewModel(),
    context: Context,
    ttsViewModel: TTSViewModel = hiltViewModel()
) {

    /**
     * Pager 기능을 구현하기 위해 아래 변수를 선언
     */
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val genderOptions = listOf("남자", "여자")

    val (voiceSelectedOption, onOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    val densityDpi = getDPI(context)

    val yesORNo = listOf("네", "아니요")

    val userState = userViewModel.user.collectAsState()
    val voice = ttsViewModel.voice.collectAsState()

    val focusManager = LocalFocusManager.current

    val enableExerciseTextField = remember {
        mutableStateOf(true)
    }

    val enableWalkingTextField = remember {
        mutableStateOf(true)
    }

    val (selectedOption, setSelectedOption) = remember {
        mutableStateOf(yesORNo[0])
    }

    val (selectedOption1, setSelectedOption1) = remember {
        mutableStateOf(yesORNo[0])
    }

    val (selectedOption2, setSelectedOption2) = remember {
        mutableStateOf(yesORNo[0])
    }

    val (selectedOption3, setSelectedOption3) = remember {
        mutableStateOf(yesORNo[0])
    }

    LaunchedEffect(user) {
        if (user.email.isNotEmpty()) {
            userViewModel.mergeAuthStateIntoUserState(user = user)
        }
    }

    LaunchedEffect(userState.value) {
        enableExerciseTextField.value = userState.value.recentExerciseCheck == "네"
        enableWalkingTextField.value = userState.value.recentWalkingCheck == "네"
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
                0 -> AgeInfo()
                1 -> Text("최근 운동 입력")
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