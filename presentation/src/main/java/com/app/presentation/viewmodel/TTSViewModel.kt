package com.app.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.Voice
import com.app.domain.usecase.tts.TTSCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * TTS 관련
 */
@HiltViewModel
class TTSViewModel @Inject constructor(
    private val ttsCase: TTSCase
) : ViewModel() {

    private val _voice = MutableStateFlow(Voice(
        languageCode = "",
        name = ""
    ))

    val voice: StateFlow<Voice> = _voice

    fun speak(text: String, type: VoiceType) {
        ttsCase.speak(text, type) {
            _voice.update { voice ->
                voice.copy(
                    languageCode = it.languageCode,
                    name = it.name
                )
            }
        }
    }

    fun insert(voice: Voice) {
        Log.d("TTSViewModel", voice.toString())
    }
}