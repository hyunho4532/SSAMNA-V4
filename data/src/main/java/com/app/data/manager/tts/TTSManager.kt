package com.app.data.manager.tts

import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.Voice

interface TTSManager {
    fun preview(text: String, type: VoiceType, setVoice: (Voice) -> Unit)
    fun speak(text: String, voice: Voice)
}