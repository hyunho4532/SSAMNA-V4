package com.app.domain.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoiceDTO (
    @SerialName("user_id")
    val userId: String = "",

    @SerialName("gender")
    val gender: String = ""
)