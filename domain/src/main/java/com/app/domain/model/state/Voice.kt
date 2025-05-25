package com.app.domain.model.state

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Voice (
    @SerialName("user_id")
    val userId: String = "",

    @SerialName("language_code")
    val languageCode: String = "",

    @SerialName("name")
    val name: String = "",

    @SerialName("time")
    val time: String = ""
)