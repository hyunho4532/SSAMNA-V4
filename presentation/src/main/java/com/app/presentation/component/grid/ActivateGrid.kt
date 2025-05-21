package com.app.presentation.component.grid

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.calcul.FormatImpl
import com.app.presentation.viewmodel.ActivityLocationViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ActivateGrid(
    yearMonth: LocalDate,
    todayList: List<String>,
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val list = (1..FormatImpl("YY:MM:DD").getMonthDays(yearMonth)).map { it }

    val activityDays = todayList.map {
        FormatImpl("YY:MM:DD").parseMonthDaysStr(it)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        content = {
            items(list.size) { index ->
                val dayLocalDate = yearMonth.withDayOfMonth(list[index]).format(FormatImpl("YY:MM:DD").formatter)
                val isToday = activityDays.contains(dayLocalDate.toString())

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = rememberRipple(
                                color = if (isToday) Color.Blue else Color.Black,
                                bounded = true
                            )
                        ) {
                            /**
                             * 클릭한 날짜에 해당하는 데이터를 찾아서 활동 내역 리스트로 조회한다.
                             */
                            coroutineScope.launch {
                                activityLocationViewModel.selectActivityFindByDate(dayLocalDate)
                            }
                        }
                        .background(
                            color = if (isToday) Color(0xFF3C69EA) else Color(0xFFE7E7E7),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    )
}