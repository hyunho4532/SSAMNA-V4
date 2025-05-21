package com.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.app.domain.model.enum.VoiceType
import com.app.domain.usecase.tts.TTSCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * TTS 관련
 */
@HiltViewModel
class TTSViewModel @Inject constructor(
    private val ttsCase: TTSCase
) : ViewModel() {
    fun speak(text: String, type: VoiceType) {
        ttsCase.speak(text, type)
    }
}