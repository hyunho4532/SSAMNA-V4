package com.app.domain.repository.tts

import com.app.domain.model.enum.VoiceType

interface TTSRepository {
    fun speak(text: String, type: VoiceType)
}