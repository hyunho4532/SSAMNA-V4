package com.app.presentation.ui.feature.login.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.user.User
import com.app.presentation.R
import com.app.presentation.component.util.Const
import com.app.presentation.viewmodel.TTSViewModel

@Composable
fun VoiceInfo(
    userState: State<User>,
    ttsViewModel: TTSViewModel = hiltViewModel()
) {
    val (voiceSelected, setVoiceSelected) = remember {
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
                text = "함께할 땀나 목소리를 선택해주세요!",
                modifier = Modifier.padding(top = 32.dp, start = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

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
                                    selected = (text == voiceSelected),
                                    onClick = {
                                        val voiceType = when(text) {
                                            "남자" -> VoiceType.MALE
                                            else -> VoiceType.FEMALE
                                        }

                                        setVoiceSelected(text)

                                        ttsViewModel.preview("안녕하세요. 저와 함께해요!", voiceType)
                                    }
                                )
                                .padding(vertical = 8.dp),
                        ) {
                            RadioButton(
                                selected = (text == voiceSelected),
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
                                    fontWeight = FontWeight.Bold
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
            }
        }
    }
}