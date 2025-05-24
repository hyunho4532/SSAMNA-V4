package com.app.domain.usecase.tts

import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.Voice
import com.app.domain.repository.tts.TTSRepository
import javax.inject.Inject

class TTSCase @Inject constructor(
    private val ttsRepository: TTSRepository
) {
    fun speak(text: String, type: VoiceType, setVoice: (Voice) -> Unit) {
        ttsRepository.speak(text, type) {
            setVoice(it)
        }
    }
}