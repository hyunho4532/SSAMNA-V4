package com.app.presentation.ui.feature.auth

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.domain.model.enum.ButtonType
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.user.User
import com.app.presentation.R
import com.app.presentation.component.dialog.ShowAccountDeleteDialog
import com.app.presentation.component.dialog.UserUpdateBottomSheet
import com.app.presentation.component.tool.CustomButton
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.util.Const
import com.app.presentation.component.util.responsive.setUpWidth
import com.app.presentation.viewmodel.StateViewModel
import com.app.presentation.viewmodel.TTSViewModel

/**
 * 사용자 설정 화면
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    user: User,
    stateViewModel: StateViewModel,
    ttsViewModel: TTSViewModel = hiltViewModel()
) {
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(Const().gender[0])
    }

    val (timeSelected, setTimeSelected) = remember {
        mutableStateOf(Const().time[0])
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    /**
     * 내 정보 수정 시 팝업 창 상태
     */
    val isInfoUser = remember {
        mutableStateOf(false)
    }

    /**
     * 계정 탈퇴 팝업 창 상태
     */
    val isDeleteAccount = remember {
        mutableStateOf(false)
    }

    val voice = ttsViewModel.voice.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = user.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(
            width = 0.dp,
            height = 30.dp
        )

        Card(
            modifier = Modifier
                .width(setUpWidth())
                .height(48.dp)
                .clickable {
                    stateViewModel.toggleTheme()
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp),
                    text = "다크 모드",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Image(
                    modifier = Modifier
                        .size(28.dp),
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                    contentDescription = "활동 아이콘",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }

        Spacer(
            width = 0.dp,
            height = 30.dp
        )

        Card(
            modifier = Modifier
                .width(setUpWidth())
                .height(260.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .selectableGroup()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Const().gender.forEach { text ->
                        Box(
                            modifier = Modifier
                                .height(120.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = {
                                        val voiceType = when (text) {
                                            "남자" -> VoiceType.MALE
                                            else -> VoiceType.FEMALE
                                        }

                                        onOptionSelected(text)
                                        ttsViewModel.preview(
                                            text = "안녕하세요! 저와 함께해요!",
                                            type = voiceType
                                        )
                                    }
                                )
                                .padding(vertical = 8.dp),
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFF2377f9)
                                )
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = text,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                if (text == "남자") {
                                    Image(
                                        modifier = Modifier
                                            .size(120.dp),
                                        painter = painterResource(R.drawable.tts_man),
                                        contentDescription = "남자 TTS 캐릭터"
                                    )
                                } else {
                                    Image(
                                        modifier = Modifier
                                            .size(120.dp),
                                        painter = painterResource(R.drawable.tts_human),
                                        contentDescription = "여자 TTS 캐릭터"
                                    )
                                }
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .selectableGroup()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Const().time.forEach { text ->
                            Box(
                                modifier = Modifier
                                    .height(62.dp)
                                    .selectable(
                                        selected = (text == timeSelected),
                                        onClick = {
                                            setTimeSelected(text)
                                            ttsViewModel.time(text)
                                        }
                                    )
                                    .padding(vertical = 8.dp),
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                ) {
                                    RadioButton(
                                        selected = (text == timeSelected),
                                        onClick = null,
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color(0xFF2377f9)
                                        )
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .padding(top = 24.dp)
                                ) {
                                    Text(
                                        text = text,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                CustomButton(
                    type = ButtonType.VoiceStatus.INSERT,
                    width = setUpWidth(),
                    height = 38.dp,
                    text = "목소리 등록하기 (땀나와 함께!)",
                    shape = "Rectangle",
                    data = voice.value,
                    stateViewModel = stateViewModel
                )
            }
        }

        Spacer(
            width = 0.dp,
            height = 30.dp
        )

        Card(
            modifier = Modifier
                .width(setUpWidth())
                .height(48.dp)
                .clickable {
                    isDeleteAccount.value = true
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp),
                    text = "계정 탈퇴",
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )

                Image(
                    modifier = Modifier
                        .size(28.dp),
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                    contentDescription = "활동 아이콘",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    }

    if (isInfoUser.value) {
        UserUpdateBottomSheet(
            showBottomSheet = isInfoUser,
            sheetState = sheetState,
            user = user
        )
    }

    if (isDeleteAccount.value) {
        ShowAccountDeleteDialog(
            isDeleteAccount = isDeleteAccount
        )
    }
}