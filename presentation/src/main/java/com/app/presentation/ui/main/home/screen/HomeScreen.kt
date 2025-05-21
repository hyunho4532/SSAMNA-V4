package com.app.presentation.ui.main.home.screen


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.location.Coordinate
import com.app.presentation.R
import com.app.presentation.component.aside.HomeAside
import com.app.presentation.component.box.TopBox
import com.app.presentation.component.dialog.ShowCompleteDialog
import com.app.presentation.component.marker.MapMarker
import com.app.presentation.component.tool.CircularProgress
import com.app.presentation.component.tool.CustomButton
import com.app.domain.model.enum.ButtonType
import com.app.presentation.viewmodel.ActivityLocationViewModel
import com.app.presentation.viewmodel.LocationManagerViewModel
import com.app.presentation.viewmodel.SensorManagerViewModel
import com.app.presentation.viewmodel.StateViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("UseOfNonLambdaOffsetOverload")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    fusedLocationClient: FusedLocationProviderClient,
    locationManagerViewModel: LocationManagerViewModel = hiltViewModel(),
    activityLocationViewModel: ActivityLocationViewModel = hiltViewModel(),
    sensorManagerViewModel: SensorManagerViewModel = hiltViewModel(),
    stateViewModel: StateViewModel = hiltViewModel(),
    context: Context
) {
    val coordinateState = locationManagerViewModel.coordinate.collectAsState()
    val locationState = activityLocationViewModel.locations.collectAsState()
    val activates by sensorManagerViewModel.activates.collectAsState()
    val activatesForm by activityLocationViewModel.activatesForm.collectAsState()

    var isLocationLoaded by remember {
        mutableStateOf(false)
    }

    val locationPermissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACTIVITY_RECOGNITION,
                Manifest.permission.POST_NOTIFICATIONS
            )
        )
    } else {
        TODO("VERSION.SDK_INT < Q")
    }

    var isPanelVisible by remember {
        mutableStateOf(false)
    }

    val panelWidth by animateDpAsState(targetValue = if (isPanelVisible) 10.dp else 0.dp,
        label = ""
    )

    val buttonOffset by animateDpAsState(targetValue = if (isPanelVisible) (-130).dp else 0.dp,
        label = ""
    )

    LaunchedEffect (key1 = Unit) {
        locationPermissionState.launchMultiplePermissionRequest()
    }

    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            activityLocationViewModel.getCurrentLocation(fusedLocationClient) {
                isLocationLoaded = true
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(locationState.value.latitude, locationState.value.latitude) {
        if (isLocationLoaded) {
            cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(LatLng(locationState.value.latitude, locationState.value.longitude), 12f)
            )
        }
    }

    val coordinatesFiltering = coordinateState.value.coordz.filter {
        it.latitude != 0.0
    }

    val coordinatesPolyline = coordinatesFiltering.map {
        LatLng(it.latitude, it.longitude)
    }

    val coordinates = coordinatesFiltering.map {
        Coordinate(
            latitude = it.latitude,
            longitude = it.longitude,
            altitude = it.altitude,
            km = it.km
        )
    }

    val mapStyles = if (stateViewModel.isDarkTheme.value) {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_dark)
    } else {
        null
    }

    val mapProperties = remember {
        MapProperties(
            mapStyleOptions = mapStyles
        )
    }

    if (locationPermissionState.allPermissionsGranted) {
        if (isLocationLoaded) {
            GoogleMap(
                cameraPositionState = cameraPositionState,
                properties = mapProperties
            ) {
                MapMarker(
                    context = context,
                    position = LatLng(locationState.value.latitude, locationState.value.longitude),
                    title = "현재 위치!",
                    iconResourceId = R.drawable.running_marker
                )

                Marker(
                    state = MarkerState(position = LatLng(activatesForm.latitude, activatesForm.longitude)),
                    title = "목표 지점",
                    snippet = "여기가 목표지점이에요!"
                )

                coordinates.forEach {
                    MapMarker(
                        context = context,
                        position = LatLng(it.latitude, it.longitude),
                        title = "달리세요!",
                        iconResourceId = R.drawable.location_marker
                    )
                }

                Polyline(
                    points = coordinatesPolyline,
                    color = Color.Gray,
                    width = 3f
                )
            }

            if (activatesForm.showMarkerPopup) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                activityLocationViewModel.closeMarkerPopup()
                            },
                        painter = painterResource(id = R.drawable.marker),
                        contentDescription = "마커 아이콘"
                    )

                    Box(
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        CustomButton(
                            type = ButtonType.MarkerStatus.FINISH,
                            width = 120.dp,
                            height = 40.dp,
                            text = "선택 완료!",
                            context = context,
                            cameraPositionState = cameraPositionState
                        )
                    }

                }
            }

            if (activates.activateButtonName == "측정 중!" || sensorManagerViewModel.getSavedIsRunningState()) {
                TopBox(context)
            }

            if (activates.showRunningStatus) {
                ShowCompleteDialog(
                    context = context,
                    locationState = locationState,
                    coordinate = coordinates,
                    sensorManagerViewModel = sensorManagerViewModel,
                )
            }

            Box (
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = { isPanelVisible = !isPanelVisible },
                    modifier = Modifier
                        .width(58.dp)
                        .height(50.dp)
                        .offset(x = buttonOffset)
                        .align(Alignment.CenterEnd),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = Color.Black
                    ),
                    shape = RectangleShape
                ) {
                    Text(
                        text = if (isPanelVisible) "→" else "←",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (isPanelVisible) {
                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(230.dp)
                            .offset(x = panelWidth)
                            .align(Alignment.CenterEnd)
                            .background(Color.White)
                            .animateContentSize()
                    ) {
                        HomeAside(
                            context = context
                        )
                    }
                }
            }

        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgress(text = "현재 위치를 조회하고 있습니다!")
            }
        }
    }
}