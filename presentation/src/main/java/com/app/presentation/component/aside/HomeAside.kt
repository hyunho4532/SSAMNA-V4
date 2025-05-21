package com.app.presentation.component.aside

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.presentation.R
import com.app.presentation.component.dialog.ActivateBottomSheet
import com.app.presentation.component.dialog.ActivateFormBottomSheet
import com.app.presentation.component.tool.CustomButton
import com.app.presentation.component.tool.Spacer
import com.app.domain.model.enum.ButtonType
import com.app.presentation.viewmodel.ActivityLocationViewModel
import com.app.presentation.viewmodel.SensorManagerViewModel
import com.app.presentation.viewmodel.StateViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAside(
    context: Context,
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
) {

    val activates by activityLocationViewModel.activates.collectAsState()
    val activatesForm by activityLocationViewModel.activatesForm.collectAsState()

    val showActivateBottomSheet = remember {
        mutableStateOf(false)
    }

    val showActivateFormBottomSheet = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    Column (
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    showActivateBottomSheet.value = true
                }
        ) {
            Text(
                text = "활동 종류",
                modifier = Modifier.padding(top = 8.dp, start = 14.dp)
            )

            Row (
                modifier = Modifier.padding(top = 32.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = activates.activateResId),
                    contentDescription = "달리는 사람 아이콘",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )

                Text(
                    text = activates.activateName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    showActivateFormBottomSheet.value = true
                }
        ) {
            Text(
                text = "활동 형태",
                modifier = Modifier.padding(top = 8.dp, start = 14.dp)
            )

            Row (
                modifier = Modifier.padding(top = 32.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = activatesForm.activateFormResId),
                    contentDescription = "운동 시간 아이콘",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )

                Spacer(width = 2.dp, height = 0.dp)

                Text(
                    text = activatesForm.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = rememberRipple(
                        color = Color.Gray,
                        bounded = true
                    )
                ) {
                    Toast.makeText(context, "목표 위치를 취소하고 싶을 땐, 마커를 클릭하세요!", Toast.LENGTH_SHORT).show()
                    activatesForm.showMarkerPopup = true
                }
        ) {
            Text(
                text = "목표 위치",
                modifier = Modifier.padding(top = 8.dp, start = 14.dp)
            )

            Row (
                modifier = Modifier.padding(top = 32.dp, start = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.baseline_fmd_good_24),
                    contentDescription = "목표 지점 아이콘",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )

                Spacer(width = 2.dp, height = 0.dp)

                Text(
                    text = "장소 선택",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

       CustomButton(
            type = ButtonType.RunningStatus.OPEN,
            width = 110.dp,
            height = 36.dp,
            text = sensorManagerViewModel.getSavedButtonNameState()!!,
            showIcon = false,
            context = context,
            shape = "Circle"
        )
    }

    ActivateFormBottomSheet(
        context = context,
        sheetState = sheetState,
        showBottomSheet = showActivateFormBottomSheet
    )

    ActivateBottomSheet(
        context = context,
        sheetState = sheetState,
        showBottomSheet = showActivateBottomSheet
    )
}