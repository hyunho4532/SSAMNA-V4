package com.app.presentation.viewmodel

import android.content.Context
import android.hardware.SensorEventListener
import android.os.Build
import android.util.Log
import android.view.Display.Mode
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.calcul.FormatImpl
import com.app.domain.model.state.Activate
import com.app.domain.usecase.sensor.SensorManagerCase
import com.app.domain.usecase.tts.TTSCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorManagerViewModel @Inject constructor(
    private val sensorManagerCase: SensorManagerCase,
    private val ttsCase: TTSCase,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private var userShared = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    private var sharedPreferences = appContext.getSharedPreferences("sensor_prefs", Context.MODE_PRIVATE)

    // StateFlow로 상태 관리
    private val _activates = MutableStateFlow(Activate(
        time = getSavedTimeState(),
        isRunning = getSavedIsRunningState(),
        pedometerCount = getSavedSensorState()
    ))

    val activates: StateFlow<Activate> = _activates

    private var stopwatchJob: Job? = null

    // 서비스 시작 시 상태 업데이트
    fun startService(isRunning: Boolean) {
        sensorManagerCase.startService(appContext)

        _activates.update {
            it.copy(
                activateButtonName = "측정 중!",
                isRunning = isRunning
            )
        }

        // 상태를 SharedPreferences에 저장
        sharedPreferences.edit().putBoolean("showRunning", isRunning).apply()
        sharedPreferences.edit().putString("activateButtonName", _activates.value.activateButtonName).apply()
    }

    // 서비스 중지 시 상태 업데이트
    fun stopService(runningStatus: Boolean, isRunning: Boolean) {
        sharedPreferences.edit().putInt("pedometerCount", 0).apply()

        sensorManagerCase.stopService(context = appContext)

        _activates.update {
            it.copy(
                showRunningStatus = runningStatus,
                isRunning = isRunning,
                activateButtonName = "측정하기!",
            )
        }

        // 상태를 SharedPreferences에 저장
        sharedPreferences.edit().putBoolean("showRunning", isRunning).apply()
        sharedPreferences.edit().putBoolean("isShowRunningBottomNavi", isRunning).apply()
        sharedPreferences.edit().putString("activateButtonName", _activates.value.activateButtonName).apply()
    }

    // 센서 이벤트 리스너
    fun sensorEventListener(): SensorEventListener {
        return sensorManagerCase.sensorListener(getSavedSensorState()) { stepCount ->
            _activates.update {
                it.copy(
                    pedometerCount = stepCount
                )
            }

            // 센서 값 SharedPreferences에 저장
            sharedPreferences.edit().putInt("pedometerCount", _activates.value.pedometerCount).apply()
        }
    }

    // SharedPreferences에서 저장된 값 가져오기
    fun getSavedSensorState(): Int {
        return sharedPreferences.getInt("pedometerCount", 0)
    }

    fun getSavedButtonNameState(): String? {
        return sharedPreferences.getString("activateButtonName", _activates.value.activateButtonName)
    }

    fun getSavedTimeState(): Long {
        return sharedPreferences.getLong("time", 0L)
    }

    fun getSavedIsRunningState(): Boolean {
        return sharedPreferences.getBoolean("showRunning", false)
    }

    // SharedPreferences에 저장
    fun setSavedSensorState() {
        sharedPreferences.edit().putInt("pedometerCount", _activates.value.pedometerCount).apply()
        sharedPreferences.edit().putLong("time", _activates.value.time).apply()
    }

    // 타이머 시작
    @RequiresApi(Build.VERSION_CODES.O)
    fun startWatch() {
        /**
         * 타이머를 시작하기 전, 현재 사용자의 Voice time 값을 가져온다.
         */
        val id = userShared.getString("id", "")
        val pedometerCount = sharedPreferences.getInt("pedometerCount", 0)

        viewModelScope.launch {
            val voiceList = ttsCase.isExists(userId = id!!)
            val voice = voiceList.firstOrNull()

            if (stopwatchJob == null) {
                stopwatchJob = viewModelScope.launch {
                    while (true) {
                        delay(1000L)
                        _activates.update {
                            val newTime = it.time + 1
                            sharedPreferences.edit().putLong("time", newTime).apply()

                            voice?.let {
                                if (voice.time.toLong() != 0L && newTime % voice.time == 0L) {
                                    ttsCase.speak(
                                        text = "현재 걸음 수: $pedometerCount, 시간은 ${FormatImpl("YY:MM:DD:H").getSpeakTime(newTime)} 입니다.",
                                        voice = voice
                                    )
                                }
                            }

                            it.copy(time = newTime)
                        }
                    }
                }
            }
        }
    }

    // 타이머 중지
    fun stopWatch() {
        stopwatchJob?.cancel()
        stopwatchJob = null
    }
}