package com.app.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.Voice
import com.app.domain.usecase.tts.TTSCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * TTS 관련
 */
@HiltViewModel
class TTSViewModel @Inject constructor(
    @ApplicationContext appContext: Context,
    private val ttsCase: TTSCase
) : ViewModel() {

    private val sharedPreferences = appContext.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val _voice = MutableStateFlow(Voice(
        languageCode = "",
        name = "",
    ))

    val voice: StateFlow<Voice> = _voice

    fun preview(text: String, type: VoiceType) {
        val userId = sharedPreferences.getString("id", "").toString()

        ttsCase.preview(text, type) {
            _voice.update { voice ->
                voice.copy(
                    userId = userId,
                    languageCode = it.languageCode,
                    name = it.name
                )
            }
        }
    }

    fun insert(voice: Voice) {
        viewModelScope.launch {
            ttsCase.insert(voice)
        }
    }

    fun speak(text: String) {
        val userId = sharedPreferences.getString("id", "").toString()

        /**
         * Voice 테이블에 내가 등록한 사운드가 존재하는지 확인
         */
        viewModelScope.launch {
            val isExists = ttsCase.isExists(userId)
        }
    }
}