package com.app.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.location.Location
import com.app.domain.model.state.Activate
import com.app.domain.model.dto.ActivateDTO
import com.app.domain.model.location.Coordinate
import com.app.domain.model.state.ActivateForm
import com.app.domain.usecase.activate.ActivateCase
import com.app.domain.model.calcul.FormatImpl
import com.app.domain.model.dto.ActivateNotificationDTO
import com.app.domain.model.dto.CrewDTO
import com.app.presentation.component.util.JsonObjImpl
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

@HiltViewModel
class ActivityLocationViewModel @Inject constructor(
    private val activateCase: ActivateCase?,
    @ApplicationContext appContext: Context?,
): ViewModel() {

    private lateinit var activateNotificationDTO: ActivateNotificationDTO
    private var sharedPreferences = appContext?.getSharedPreferences("sensor_prefs", Context.MODE_PRIVATE)
    private val sharedPreferences2 = appContext?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _locations = MutableStateFlow(Location(
        latitude = 0.0,
        longitude = 0.0
    ))

    private val _activates = MutableStateFlow(Activate(
        time = sharedPreferences!!.getLong("time", 0L)
    ))

    private val _activatesForm = MutableStateFlow(ActivateForm())

    private val _activateData = MutableStateFlow<List<ActivateDTO>>(emptyList())

    val activateData: StateFlow<List<ActivateDTO>> = _activateData

    val locations: StateFlow<Location> = _locations
    val activates: StateFlow<Activate> = _activates
    val activatesForm: StateFlow<ActivateForm> = _activatesForm

    /**
     *
     */
    private val _isPublic = MutableStateFlow(false)
    val isPublic: StateFlow<Boolean> = _isPublic

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        fusedLocationClient: FusedLocationProviderClient,
        isLocationLoaded: (Boolean) -> Unit
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                _locations.value = Location(
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                isLocationLoaded(true)
            } else {
                isLocationLoaded(false)
            }
        }
    }

    fun setActivateName(activateResId: Int, activateName: String) {
        _activates.update {
            it.copy(
                activateResId = activateResId,
                activateName = activateName
            )
        }
    }

    fun setActivateFormName(activateFormResId: Int, activateFormName: String) {
        _activatesForm.update {
            it.copy(
                activateFormResId = activateFormResId,
                name = activateFormName
            )
        }
    }

    fun closeMarkerPopup() {
        _activatesForm.update {
            it.copy(
                showMarkerPopup = false
            )
        }
    }

    fun setLatLng(
        latitude: Double,
        longitude: Double
    ) {
        _activatesForm.update {
            it.copy(
                latitude = latitude,
                longitude = longitude,
                showMarkerPopup = false
            )
        }
    }

    fun statusClick(name: String, resId: Int) {
        _activates.update {
            it.copy(
                statusName = name,
                statusIcon = resId
            )
        }
    }

    fun updateRunningTitle(newTitle: String) {
        _activates.value = _activates.value.copy(runningTitle = newTitle)
    }


    /**
     * 활동 저장 버튼 클릭 시 활동 테이블에 데이터 저장
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveActivity(
        runningIcon: Int,
        runningTitle: String,
        coordinate: List<Coordinate>,
        crew: State<List<CrewDTO>>,
        userName: String,
    ) {
        val pedometerCount = sharedPreferences?.getInt("pedometerCount", _activates.value.pedometerCount)
        val userId = sharedPreferences2?.getString("id", "")
        val time = sharedPreferences?.getLong("time", 0L)

        /**
         * kcal_cul, km_cul를 JSON 형태로 만든다.
         */
        val culData = JsonObjImpl(
            type = "cul",
            pedometerCount = pedometerCount!!
        ).build()

        val statusData = JsonObjImpl(
            type = "status",
            activate = _activates
        ).build()

        val runningData = JsonObjImpl(
            type = "running",
            runningList = arrayOf(runningIcon, runningTitle)
        ).build()

        val runningFormData = JsonObjImpl(
            type = "runningForm",
            activateForm = _activatesForm
        ).build()

        val coordinateData = JsonObjImpl(
            type = "coordinate",
            coordinateList = coordinate
        ).build()

        val crewData = JsonObjImpl(
            type = "crewId",
            crewList = crew
        ).build()

        val activateDTO = ActivateDTO (
            userId = userId!!,
            title = _activates.value.runningTitle,
            coord = coordinateData,
            status = statusData,
            running = runningData,
            runningForm = runningFormData,
            isPublic = _isPublic.value,
            time = FormatImpl("YY:MM:DD:H").getFormatTime(time!!),
            cul = culData,
            todayFormat = FormatImpl("YY:MM:DD:H").getTodayFormatDate(),
            eqDate = FormatImpl("YY:MM:DD").getTodayFormatDate()
        )

        activateNotificationDTO = if (crew.value.isEmpty()) {
            Log.d("ActivityLocationViewModel", _isPublic.value.toString())

            ActivateNotificationDTO (
                userId = userId,
                feed = pedometerCount,
                km = FormatImpl("YY:MM:DD:H").calculateDistanceToKm(pedometerCount),
                crewId = buildJsonObject {
                    put("idx", buildJsonArray {
                        add(buildJsonObject {
                            put("id", 0)
                        })
                    })
                },
                userName = userName,
                isPublic = _isPublic.value,
                createdAt = FormatImpl("YY:MM:DD:H").getTodayFormatDate(),
            )
        } else {
            ActivateNotificationDTO (
                userId = userId,
                feed = pedometerCount,
                km = FormatImpl("YY:MM:DD:H").calculateDistanceToKm(pedometerCount),
                crewId = crewData,
                userName = userName,
                isPublic = _isPublic.value,
                createdAt = FormatImpl("YY:MM:DD:H").getTodayFormatDate(),
            )
        }

        viewModelScope.launch {
            activateCase?.saveActivity(activateDTO = activateDTO) {
                sharedPreferences?.edit()!!.putLong("time", it).apply()
            }

            activateCase?.saveActivityNotification(activateNotificationDTO = activateNotificationDTO)
        }
    }

    fun deleteActivityFindById(googleId: String, date: String, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            activateCase?.deleteActivity(googleId, date) {
                onSuccess(it)
            }
        }
    }

    suspend fun selectActivityFindByGoogleId(googleId: String) {
        val activateDTO = activateCase?.selectActivityFindByGoogleId(googleId)
        _activateData.value = activateDTO!!
    }

    suspend fun selectActivityFindById(id: Int) {
        val activateDTO = activateCase?.selectActivityFindById(id)
        _activateData.value = activateDTO!!
    }

    suspend fun selectActivityFindByDate(date: String) {
        val googleId = sharedPreferences2?.getString("id", "")

        val activateDTO = activateCase?.selectActivityFindByDate(googleId!!, date)
        _activateData.value = activateDTO!!
    }

    fun setCoordList(activateData: State<List<ActivateDTO>>): List<Coordinate> {
        var coordList: List<Coordinate> = listOf()

        activateData.value.forEach { data ->
            val jsonElement = data.coord["coords"]
            val jsonString = jsonElement.toString()

            coordList = Json.decodeFromString<List<Coordinate>>(jsonString)
        }

        return coordList
    }

    /**
     * 활동 공개 여부 (switch가 변경 될 때마다 데이터 수정 또는 변경
     */
    fun changeUseStatus(isPublic: Boolean) {
        _isPublic.value = isPublic
    }
}