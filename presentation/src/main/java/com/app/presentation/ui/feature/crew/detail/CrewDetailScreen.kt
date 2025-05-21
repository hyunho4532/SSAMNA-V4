package com.app.presentation.ui.feature.crew.detail

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.domain.model.dto.CrewDTO
import com.app.presentation.component.row.ActivateTabRow
import com.app.presentation.component.tool.CustomButton
import com.app.domain.model.enum.ButtonType
import com.app.presentation.component.tool.Spacer
import com.app.presentation.viewmodel.CrewViewModel
import com.app.presentation.viewmodel.StateViewModel

@Composable
fun CrewDetailScreen(
    crewList: List<CrewDTO>,
    context: Context,
    crewViewModel: CrewViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        crewViewModel.notificationAll()
    }

    val pages = listOf("랭킹", "기록")

    val notificationData = crewViewModel.notification.collectAsState()

    val crewId = remember {
        mutableIntStateOf(0)
    }

    val crewCount = remember {
        mutableIntStateOf(0)
    }

    val crewSumFeed = remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        crewList.forEach { crew ->

            LaunchedEffect(key1 = Unit) {
                crewCount.intValue = crewViewModel.crewCount(crew.crewId)
                crewSumFeed.intValue = crewViewModel.crewSumFeed(crew.crewId)
            }

            crewId.intValue = crew.crewId

            val imageName = crew.picture.replace("R.drawable.", "")
            val imageResId = context.resources.getIdentifier(
                imageName,
                "drawable",
                context.packageName
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = imageResId),
                    contentDescription = "크루 상세 이미지",
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .padding(top = 12.dp, start = 6.dp, end = 6.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = crew.title,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        CustomButton(
                            type = ButtonType.CrewStatus.Delete,
                            width = 100.dp,
                            height = 32.dp,
                            text = "크루 탈퇴",
                            crewId = crew.crewId
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Card(
                            modifier = Modifier
                                .width(120.dp)
                                .height(80.dp),
                            border = BorderStroke(0.5.dp, Color.Gray)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "크루원",
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = crewCount.intValue.toString() + "명",
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .width(120.dp)
                                .height(80.dp),
                            border = BorderStroke(0.5.dp, Color.Gray)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "피드",
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = crewSumFeed.intValue.toString(),
                                    fontSize = 15.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(width = 0.dp, height = 20.dp)

        ActivateTabRow(
            pages = pages,
            dataList = notificationData.value,
            crewId = crewId.intValue,
            type = "crew"
        )
    }
}