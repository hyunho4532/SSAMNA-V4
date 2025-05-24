package com.app.data.repository.tts

import com.app.data.manager.tts.TTSManager
import com.app.domain.model.enum.VoiceType
import com.app.domain.model.state.Voice
import com.app.domain.repository.tts.TTSRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import javax.inject.Inject

class TTSRepositoryImpl @Inject constructor(
    private val ttsManager: TTSManager,
    private val postgrest: Postgrest
) : TTSRepository {
    override fun speak(text: String, type: VoiceType, setVoice: (Voice) -> Unit) {
        ttsManager.speak(text, type) {
            setVoice(it)
        }
    }

    override suspend fun insert(voice: Voice) {
        return withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("p_user_id", JsonPrimitive(voice.userId))
                put("p_name", JsonPrimitive(voice.name))
                put("p_language_code", JsonPrimitive(voice.languageCode))
            }

            postgrest.rpc("insert_voice", params)
        }
    }
}