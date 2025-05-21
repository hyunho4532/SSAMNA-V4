package com.app.presentation.ui.main.home.screen

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.domain.model.dto.ActivateDTO
import com.app.domain.model.user.User
import com.app.presentation.R
import com.app.presentation.component.admob.Banner
import com.app.presentation.component.grid.ActivateGrid
import com.app.presentation.component.row.ActivateTabRow
import com.app.presentation.component.util.responsive.setUpWidth
import com.app.presentation.component.dialog.ActivateDetailBottomSheet
import com.app.presentation.viewmodel.ActivityLocationViewModel
import com.app.presentation.viewmodel.StateViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    activateList: List<ActivateDTO>,
    userList: State<User>,
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    navController: NavController,
    stateViewModel: StateViewModel,
) {
    val activateData = activityLocationViewModel.activateData.collectAsState()
    val pages = listOf("매주", "매달", "연간")

    var currentMonth by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mutableStateOf(LocalDate.now())
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    val todayList: List<String> = activateList.map {
        it.todayFormat.substring(0, 13)
    }

    val isPopup = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    Column(
        modifier = Modifier
            .padding(top = 12.dp, start = 12.dp)
    ) {
        Text(
            text = "${userList.value.name}님의 활동 내역",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    currentMonth = currentMonth.minusMonths(1)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
                    contentDescription = "저번 월로 이동"
                )
            }

            Text(
                text = "${currentMonth.year}년 ${
                    currentMonth.monthValue.toString().padStart(2, '0')
                }일",
                fontSize = 16.sp
            )

            IconButton(
                onClick = {
                    currentMonth = currentMonth.plusMonths(1)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                    contentDescription = "다음 월로 이동"
                )
            }
        }

        Box(
            modifier = Modifier
                .width(setUpWidth())
                .padding(top = 12.dp)
        ) {
            ActivateGrid(
                yearMonth = currentMonth,
                todayList = todayList
            )
        }

        Box(
            modifier = Modifier
                .width(setUpWidth())
        ) {
            if (activateData.value.isNotEmpty()) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        text = "활동 내역 중 ${activateData.value.size}개의 내역이 있습니다!",
                        modifier = Modifier
                            .clickable {
                                isPopup.value = true
                            }
                            .padding(top = 8.dp),
                        fontSize = 14.sp
                    )
                }

            } else {
                Text(
                    text = "이런, 활동 내역이 없습니다. ㅠㅠ",
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .align(Alignment.Center),
                    fontSize = 14.sp
                )
            }
        }

        Box (
            modifier = Modifier
                .padding(top = 8.dp)
                .width(setUpWidth())
                .align(Alignment.CenterHorizontally)
        ) {
            Banner()
        }

        Box(
            modifier = Modifier
                .width(setUpWidth())
                .padding(top = 12.dp)
        ) {
            ActivateTabRow(
                pages = pages,
                dataList = activateList,
                type = "activate"
            )
        }
    }

    if (isPopup.value) {
        ActivateDetailBottomSheet(
            showBottomSheet = isPopup,
            sheetState = sheetState,
            activateData = activateData,
            navController = navController
        )
    }
}