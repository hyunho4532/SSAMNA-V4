package com.app.data.manager.tts

import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.Voice

interface TTSManager {
    fun speak(text: String, type: VoiceType, setVoice: (Voice) -> Unit)
}