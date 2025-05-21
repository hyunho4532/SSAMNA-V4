package com.app.data.manager.tts

import com.app.domain.model.enum.VoiceType

interface TTSManager {
    fun speak(text: String, type: VoiceType)
}