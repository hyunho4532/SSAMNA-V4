package com.app.presentation.component.row

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.dto.ActivateDTO
import com.app.domain.model.entry.StepEntry
import com.app.domain.model.entry.KcalEntry
import com.app.domain.model.entry.KmEntry
import com.app.domain.model.state.Ranking
import com.app.presentation.component.row.tab.activate.Month
import com.app.presentation.component.row.tab.activate.Week
import com.app.presentation.component.row.tab.activate.Year
import com.app.presentation.component.row.tab.crew.Notification
import com.app.presentation.component.row.tab.crew.Ranking
import com.app.presentation.viewmodel.CrewViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <T> ActivateTabRow(
    pages: List<String>,
    dataList: List<T>,
    crewId: Int = 0,
    type: String,
    crewViewModel: CrewViewModel = hiltViewModel()
) {
    val rankingList = remember {
        mutableStateListOf<Ranking>()
    }

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val kcalList = dataList.mapNotNull {
        if (it is ActivateDTO) {
            it.cul["kcal_cul"]?.jsonPrimitive?.doubleOrNull?.let { kcal ->
                KcalEntry(it.todayFormat.substring(0, 13), kcal)
            }
        } else null
    }

    val kmList = dataList.mapNotNull {
        if (it is ActivateDTO) {
            it.cul["km_cul"]?.jsonPrimitive?.doubleOrNull?.let { km ->
                KmEntry(it.todayFormat.substring(0, 13), km)
            }
        } else null
    }

    val stepList = dataList.mapNotNull {
        if (it is ActivateDTO) {
            it.cul["goal_count"]?.jsonPrimitive?.intOrNull?.let { steps ->
                StepEntry(it.todayFormat.substring(0, 13), steps)
            }
        } else null
    }

    LaunchedEffect(key1 = Unit) {
        val ranking = crewViewModel.crewRankingTop3(crewId = crewId)

        rankingList.addAll(ranking)
    }

    TabRow(
        modifier = Modifier.padding(8.dp),
        backgroundColor = MaterialTheme.colorScheme.surface,
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                color = Color.Gray
            )
        }
    ) {
        pages.forEachIndexed { index, title ->
            Tab(
                text = { Text(text = title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }

    HorizontalPager(
        modifier = Modifier
            .padding(top = 56.dp),
        count = pages.size,
        state = pagerState
    ) { page ->
        if (type == "activate") {
            when (page) {
                0 -> Week(
                    kcalList = kcalList,
                    kmList = kmList,
                    stepList = stepList
                )
                1 -> Month(
                    kcalList = kcalList,
                    kmList = kmList,
                    stepList = stepList
                )
                2 -> Year(
                    kcalList = kcalList,
                    kmList = kmList,
                    stepList = stepList
                )
            }
        } else if (type == "crew") {
            when (page) {
                0 -> Ranking(
                    rankingList = rankingList
                )
                1 -> Notification(
                    crewId = crewId,
                    notificationList = dataList,
                )
            }
        }
    }
}