package com.app.presentation.component.tool

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.dto.ActivateDTO
import com.app.domain.model.dto.ChallengeDTO
import com.app.domain.model.location.Coordinate
import com.app.domain.model.state.ChallengeSub
import com.app.presentation.R
import com.app.presentation.component.util.responsive.setUpButtonWidth
import com.app.domain.model.enum.ButtonType
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.ChallengeMaster
import com.app.domain.model.state.CrewMaster
import com.app.domain.model.state.Voice
import com.app.presentation.ui.main.home.HomeActivity
import com.app.presentation.viewmodel.ActivityLocationViewModel
import com.app.presentation.viewmodel.ChallengeViewModel
import com.app.presentation.viewmodel.CrewViewModel
import com.app.presentation.viewmodel.LocationManagerViewModel
import com.app.presentation.viewmodel.SensorManagerViewModel
import com.app.presentation.viewmodel.StateViewModel
import com.app.presentation.viewmodel.TTSViewModel
import com.app.presentation.viewmodel.UserViewModel
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

@Composable
fun CustomButton(
    type: ButtonType,
    width: Dp,
    height: Dp,
    text: String,
    showIcon: Boolean = false,
    onNavigateToCheck: (Boolean) -> Unit = {},
    shape: String = "Circle",
    data: Any? = null,
    crewId: Int? = 0,
    onClick: (permissionPopup: Boolean) -> Unit = { },
    @ApplicationContext context: Context = LocalContext.current,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    coordinate: List<Coordinate> = emptyList(),
    activateData: State<List<ActivateDTO>>? = null,
    locationManagerViewModel: LocationManagerViewModel = hiltViewModel(),
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    challengeViewModel: ChallengeViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
    crewViewModel: CrewViewModel = hiltViewModel(),
    stateViewModel: StateViewModel = StateViewModel(),
    ttsViewModel: TTSViewModel = hiltViewModel()
) {
    val activates = activityLocationViewModel.activates.collectAsState()
    val activatesForm = activityLocationViewModel.activatesForm.collectAsState()

    val crew = crewViewModel.crew.collectAsState()


    val googleId = userViewModel.getSavedLoginState()
    val username = userViewModel.getSavedLoginName()

    /**
     * 다크 모드에 따라서 버튼의 색상을 다르게 보여준다
     */
    val background = if (stateViewModel.isDarkTheme.value) {
        Color.Black
    } else {
        Color(0xFF5c9afa)
    }


    LaunchedEffect(key1 = Unit) {
        this.launch {
            crewViewModel.crewFindById(googleId)
        }
    }

    Button(
        onClick = {
            when (type) {
                ButtonType.EventStatus.ROUTE -> {
                    onClick(true)
                }
                ButtonType.EventStatus.DARKTHEME -> {
                    /**
                     * 클릭 시, 테마가 바뀐다./
                     */
                    stateViewModel.toggleTheme()
                }
                ButtonType.PermissionStatus.USERCANCEL -> {
                    onNavigateToCheck(false)
                }
                ButtonType.PermissionStatus.PRIVACYCANCEL -> {
                    onNavigateToCheck(false)
                }
                ButtonType.PermissionStatus.USERCLICK -> {
                    onNavigateToCheck(true)
                }
                ButtonType.PermissionStatus.PRIVACYCLICK -> {
                    onNavigateToCheck(true)
                }
                ButtonType.MarkerStatus.FINISH -> {
                    activityLocationViewModel.setLatLng(
                        latitude = cameraPositionState.position.target.latitude,
                        longitude = cameraPositionState.position.target.longitude
                    )
                }
                else -> {
                    when (type) {
                        ButtonType.RunningStatus.FINISH -> {
                            if (sensorManagerViewModel.getSavedSensorState() < 100) {
                                sensorManagerViewModel.stopService(
                                    runningStatus = true,
                                    isRunning = false
                                )
                                locationManagerViewModel.stopService()
                                sensorManagerViewModel.stopWatch()
                            } else {
                                Toast.makeText(context, "최소 100보 이상은 걸어야 합니다!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        ButtonType.RunningStatus.InsertStatus.RUNNING -> {
                            userViewModel.selectUserFindById(googleId = googleId)

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                activityLocationViewModel.saveActivity(
                                    runningIcon = activates.value.activateResId,
                                    runningTitle = activates.value.activateName,
                                    coordinate = coordinate,
                                    crew = crew,
                                    userName = username
                                )
                                locationManagerViewModel.clearCoordinate()
                            }
                        }

                        ButtonType.RunningStatus.InsertStatus.CHALLENGE -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                if (data is ChallengeMaster) {
                                    challengeViewModel.saveChallenge(data)
                                }
                            }
                        }

                        ButtonType.RunningStatus.DeleteStatus.RUNNING -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                activityLocationViewModel.deleteActivityFindById(
                                    googleId = activateData!!.value[0].googleId,
                                    date = activateData.value[0].todayFormat
                                ) {
                                    if (it) {
                                        val intent = Intent(context, HomeActivity::class.java)
                                        context.startActivity(intent)

                                        Toast.makeText(context, "활동 내역을 삭제했습니다!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "삭제 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }

                        ButtonType.RunningStatus.DeleteStatus.CHALLENGE -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                challengeViewModel.deleteChallenge(data as ChallengeDTO) {
                                    if (it) {
                                        val intent = Intent(context, HomeActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        context.startActivity(intent)

                                        val handler = Handler(Looper.getMainLooper())
                                        handler.postDelayed({ Toast.makeText(context, "챌린지 내역을 삭제했습니다!", Toast.LENGTH_SHORT).show() }, 0)
                                    }
                                }
                            }
                        }

                        ButtonType.CrewStatus.INSERT -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                crewViewModel.saveCrew(data as CrewMaster)
                            }
                        }

                        ButtonType.CrewStatus.Delete -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                crewViewModel.deleteCrew(crewId!!, googleId) {
                                    if (it) {
                                        val intent = Intent(context, HomeActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        context.startActivity(intent)
                                    }
                                }
                            }
                        }

                        ButtonType.VoiceStatus.INSERT -> {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                ttsViewModel.insert(data as Voice)
                            }
                        }

                        else -> {
                            locationManagerViewModel.startService()
                            sensorManagerViewModel.startService(true)
                            sensorManagerViewModel.startWatch()
                        }
                    }
                }
            }
        },
        modifier = Modifier
            .wrapContentSize()
            .width(setUpButtonWidth(cardWidth = width))
            .height(height),
        colors = ButtonDefaults.buttonColors(
            containerColor = background
        ),
        shape = if (shape == "Circle") CircleShape else RectangleShape
    ) {
        if (showIcon) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_water_drop_24),
                contentDescription = "운동 여정하기!"
            )

            Spacer(width = 8.dp, height = 0.dp)
        }

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}