package com.app.presentation.component.row.tab.crew

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.domain.model.dto.ActivateNotificationDTO
import com.app.presentation.component.util.responsive.setUpWidth
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun <T> Notification(
    crewId: Int,
    notificationList: List<T>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        notificationList.forEach { notification ->
            if (notification is ActivateNotificationDTO) {
                val idxArray = notification.crewId["idx"]?.jsonArray

                val matchingIdx = idxArray!!.firstOrNull {
                    it.jsonObject["id"]?.jsonPrimitive?.int == crewId
                }

                if (matchingIdx != null) {
                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp, start = 6.dp, bottom = 4.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Card(
                            modifier = Modifier
                                .size(width = setUpWidth(), height = 60.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 1.5.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 4.dp, start = 6.dp),
                                    text = notification.userName,
                                    fontSize = 14.sp,
                                )

                                Text(
                                    modifier = Modifier.padding(top = 4.dp, end = 6.dp),
                                    text = notification.createdAt,
                                    fontSize = 14.sp,
                                )
                            }

                            Text(
                                modifier = Modifier.padding(start = 6.dp),
                                text = "오늘은 ${notification.km}km 달렸습니다!",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}