package com.app.domain.repository.tts

import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.Voice

interface TTSRepository {
    fun preview(text: String, type: VoiceType, setVoice: (Voice) -> Unit)
    suspend fun isExists(userId: String) : List<Voice>
    suspend fun insert(voice: Voice)
}