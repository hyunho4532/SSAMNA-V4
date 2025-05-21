package com.app.presentation.ui.feature.auth

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.app.presentation.component.tool.CustomButton
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.util.responsive.setUpWidth
import com.app.presentation.viewmodel.StateViewModel
import com.app.presentation.viewmodel.TTSViewModel

/**
 * 사용자 설정 화면
 */
@Composable
fun SettingScreen(
    user: User,
    stateViewModel: StateViewModel,
    ttsViewModel: TTSViewModel = hiltViewModel()
) {
    val genderOptions = listOf("남자", "여자")
    val (selectedOption, onOptionSelected) = remember {
        mutableStateOf(genderOptions[0])
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = user.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(width = 0.dp, height = 30.dp)

        Card(
            modifier = Modifier
                .width(setUpWidth())
                .height(48.dp),
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
                    text = "로그아웃",
                    fontWeight = FontWeight.Bold
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
                .height(200.dp),
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
                        .padding(vertical = 8.dp)
                ) {
                    genderOptions.forEach { text ->
                        Box(
                            modifier = Modifier
                                .width(200.dp)
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) }
                                )
                                .padding(vertical = 8.dp),
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            width = 0.dp,
            height = 30.dp
        )

        CustomButton(
            type = ButtonType.EventStatus.DARKTHEME,
            width = setUpWidth(),
            height = 46.dp,
            text = "다크 모드 활성화",
            showIcon = true,
            shape = "Rectangle",
            stateViewModel = stateViewModel
        )
    }
}