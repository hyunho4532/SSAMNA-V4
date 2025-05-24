package com.app.data.repository.tts

import com.app.data.manager.tts.TTSManager
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.Voice
import com.app.domain.repository.tts.TTSRepository
import javax.inject.Inject

class TTSRepositoryImpl @Inject constructor(
    private val ttsManager: TTSManager
) : TTSRepository {
    override fun speak(text: String, type: VoiceType, setVoice: (Voice) -> Unit) {
        ttsManager.speak(text, type) {
            setVoice(it)
        }
    }
}