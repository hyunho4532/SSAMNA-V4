package com.app.presentation.component.box

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.presentation.R
import com.app.presentation.component.icon.Footprint
import com.app.presentation.component.tool.CustomButton
import com.app.presentation.component.tool.Spacer
import com.app.domain.model.calcul.FormatImpl
import com.app.domain.model.enum.ButtonType
import com.app.presentation.viewmodel.LocationManagerViewModel
import com.app.presentation.viewmodel.SensorManagerViewModel
import com.app.presentation.viewmodel.StateViewModel

/**
 * 구글 지도에서 맨 위에 측정 중인 상태에서 걸음 수를 보여준다.
 */
@SuppressLint("NewApi")
@Composable
fun TopBox(
    context: Context,
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    stateViewModel: StateViewModel = hiltViewModel()
) {
    val activates = sensorManagerViewModel.activates.collectAsState()

    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val listener = remember {
        sensorManagerViewModel.sensorEventListener()
    }

    val background = if (stateViewModel.isDarkTheme.value) {
        Color(0xff121212)
    } else {
        Color.White
    }

    val stepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

    DisposableEffect(Unit) {
        stepDetector?.let {
            sensorManager.registerListener(listener, it, SensorManager.SENSOR_DELAY_UI)
            sensorManagerViewModel.startWatch()
        }

        onDispose {
            sensorManager.unregisterListener(listener)
            sensorManagerViewModel.setSavedSensorState()
        }
    }


    Box(
        modifier = Modifier
            .height(50.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(46.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "걸음",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(width = 2.dp, height = 0.dp)

                        Icon(
                            imageVector = Footprint,
                            contentDescription = "걸음 아이콘",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = "${sensorManagerViewModel.getSavedSensorState()}",
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.BottomCenter),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(46.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "음악",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(width = 2.dp, height = 0.dp)

                        Icon(
                            painter = painterResource(id = R.drawable.baseline_music_24),
                            contentDescription = "음악 아이콘",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Text(
                        text = "선택!",
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.BottomCenter)
                            .clickable {
                                val intent = Intent(Intent.ACTION_MAIN)
                                intent.setPackage("com.google.android.apps.youtube.music")
                                intent.addCategory(Intent.CATEGORY_LAUNCHER)

                                context.startActivity(intent)
                            },
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(46.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .height(24.dp)
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "시간",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(width = 2.dp, height = 0.dp)

                        Icon(
                            imageVector = Footprint,
                            contentDescription = "시간 아이콘",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = FormatImpl("YY:MM:DD:H").getFormatTime(activates.value.time),
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .align(Alignment.BottomCenter),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(end = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                CustomButton(
                    type = ButtonType.RunningStatus.FINISH,
                    width = 110.dp,
                    height = 32.dp,
                    text = "측정 완료!",
                    context = context,
                    shape = "Circle"
                )
            }
        }
    }
}