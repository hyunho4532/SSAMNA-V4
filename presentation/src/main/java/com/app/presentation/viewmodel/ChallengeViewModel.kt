package com.app.presentation.viewmodel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.state.ChallengeSub
import com.app.domain.model.dto.ChallengeDTO
import com.app.domain.usecase.challenge.ChallengeCase
import com.app.domain.model.calcul.FormatImpl
import com.app.domain.model.state.ChallengeMaster
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeCase: ChallengeCase,
    @ApplicationContext appContext: Context
): ViewModel() {

    private val sharedPreferences = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _challengeData = MutableStateFlow<List<ChallengeDTO>>(emptyList())
    val challengeData: StateFlow<List<ChallengeDTO>> = _challengeData

    private val _challengeDetailData = MutableStateFlow<List<ChallengeDTO>>(emptyList())
    val challengeDetailData: StateFlow<List<ChallengeDTO>> = _challengeDetailData

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveChallenge(data: ChallengeMaster) {
        val googleId = sharedPreferences.getString("id", "")

        try {
            val challengeSub = ChallengeSub(
                googleId = googleId!!,
                title = data.name,
                description = data.description,
                goal = data.goal,
                type = data.type,
                todayDate = FormatImpl("YY:MM:DD:H").getTodayFormatDate()
            )

            viewModelScope.launch {
                challengeCase.saveChallenge(challengeSub)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun selectChallengeAll(): List<ChallengeMaster> {
        return challengeCase.selectChallengeAll()
    }

    fun selectChallengeById(id: Int, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            val challengeDTO = challengeCase.selectChallengeFindById(id)
            _challengeDetailData.value = challengeDTO
            onSuccess(true)
        }
    }

    suspend fun selectChallengeByGoogleId(googleId: String) {
        val challengeDTO = challengeCase.selectChallengeFindByGoogleId(googleId!!)
        _challengeData.value = challengeDTO
    }

    fun deleteChallenge(challengeDTO: ChallengeDTO, onSuccess: (Boolean) -> Unit) {
        viewModelScope.launch {
            challengeCase.deleteChallenge(challengeDTO.id) {
                onSuccess(it)
            }
        }
    }
}