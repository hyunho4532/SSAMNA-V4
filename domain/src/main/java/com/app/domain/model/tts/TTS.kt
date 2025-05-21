package com.app.domain.model.tts

data class Input(
    val text: String
)

data class Voice(
    val languageCode: String,
    val name: String
)

data class AudioConfig(
    val audioEncoding: String = "MP3"
)

data class TTSRequest(
    val input: Input,
    val voice: Voice,
    val audioConfig: AudioConfig
)

data class TTSResponse(
    val audioContent: String
)