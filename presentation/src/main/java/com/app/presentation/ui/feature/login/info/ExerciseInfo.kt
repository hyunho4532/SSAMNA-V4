package com.app.presentation.ui.feature.login.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.user.User
import com.app.presentation.R
import com.app.presentation.component.row.RadioRow
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.util.Const
import com.app.presentation.viewmodel.UserViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ExerciseInfo(
    userState: State<User>,
    userViewModel: UserViewModel = hiltViewModel(),
    pagerState: PagerState
) {
    val coroutineScope = rememberCoroutineScope()

    val enableExerciseTextField = remember {
        mutableStateOf(true)
    }

    val (exerciseSelected, setExerciseSelected) = remember {
        mutableStateOf(Const().yesORNo[0])
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
                text = "최근 운동을 하신 적이 있으신가요?",
                modifier = Modifier.padding(top = 36.dp, start = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            RadioRow(
                yesORNo = Const().yesORNo,
                id = 0,
                selectedOption = exerciseSelected,
                onOptionSelected = setExerciseSelected
            )

            Spacer(
                width = 0.dp,
                height = 42.dp
            )

            Text(
                text = "최근 운동을 진행하셨다면 어떤 운동을 하셨나요?",
                modifier = Modifier.padding(start = 16.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            Box(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .width(240.dp)
                        .height(56.dp),
                    value = userState.value.recentExerciseName,
                    onValueChange = {
                        userViewModel.saveExerciseName(it)
                    },
                    singleLine = true,
                    enabled = enableExerciseTextField.value,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.hint_recent_exercise),
                            color = Color.Gray
                        )
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                if (userState.value.recentExerciseName.isNotEmpty()) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(2)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = if (userState.value.recentExerciseName.isNotEmpty()) Color(0xFF5c9afa) else Color.Gray
        ) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Floating action button."
            )
        }
    }
}