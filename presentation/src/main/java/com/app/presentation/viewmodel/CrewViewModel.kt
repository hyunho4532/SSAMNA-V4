package com.app.presentation.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.calcul.FormatImpl
import com.app.domain.model.dto.ActivateNotificationDTO
import com.app.domain.model.dto.CrewDTO
import com.app.domain.model.state.CrewSub
import com.app.domain.model.state.CrewMaster
import com.app.domain.model.state.Ranking
import com.app.domain.usecase.crew.CrewCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrewViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    private val crewCase: CrewCase
): ViewModel() {
    private val sharedPreferences = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    /**
     * 크루 관련 데이터
     */
    private val _crew = MutableStateFlow<List<CrewDTO>>(emptyList())
    val crew: StateFlow<List<CrewDTO>> = _crew

    /**
     * 알림 관련 데이터
     */
    private val _notification = MutableStateFlow<List<ActivateNotificationDTO>>(emptyList())
    val notification: StateFlow<List<ActivateNotificationDTO>> = _notification

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCrew(data: CrewMaster) {

        val userId = sharedPreferences.getString("id", "").toString()

        val crewDTO = CrewDTO(
            userId = userId,
            title = data.name,
            picture = data.assets,
            createdAt = FormatImpl("YY:MM:DD:H").getTodayFormatDate(),
            crewId = data.id
        )

        viewModelScope.launch {
            crewCase.insert(crewDTO)
        }
    }

    /**
     * 크루 데이터와 활동 기록 데이터 삭제
     */
    fun deleteCrew(crewId: Int, googleId: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            crewCase.delete(crewId, googleId) {
                onResult(it)
            }
        }
    }

    /**
     * 크루 데이터가 이미 존재하는지에 대한 함수
     */
    suspend fun isCrewDataExists(googleId: String): List<CrewDTO> {
        return crewCase.isCrewDataExists(googleId)
    }

    /**
     * 크루 마스터 데이터 전체 조회
     */
    suspend fun crewMasterAll(): List<CrewMaster> {
        return crewCase.crewMasterAll()
    }

    /**
     * 크루 데이터 조회
     * googleId 필터 처리
     */
    suspend fun crewFindById(googleId: String) {
        val crewDTO = crewCase.crewFindById(googleId)
        _crew.value = crewDTO
    }

    /**
     * 알림 데이터 전체 조회
     */
    suspend fun notificationAll() {
        val notificationDTO = crewCase.notificationAll()
        _notification.value = notificationDTO
    }

    /**
     * 크루 데이터 개수 조회
     * 크루원 사용 용도
     */
    suspend fun crewCount(crewId: Int): Int {
        return crewCase.crewCount(crewId)
    }

    /**
     * 크루원 피드 합계 조회
     * 피드 조회
     */
    suspend fun crewSumFeed(crewId: Int): Int {
        return crewCase.crewSumFeed(crewId)
    }

    /**
     * 크루 랭킹 TOP3 데이터 조회
     */
    suspend fun crewRankingTop3(crewId: Int): List<Ranking> {
        return crewCase.crewRankingTop3(crewId)
    }
}