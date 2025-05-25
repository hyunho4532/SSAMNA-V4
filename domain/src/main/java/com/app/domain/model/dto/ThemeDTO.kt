package com.app.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThemeDTO (
    @SerialName("user_id")
    val userId: String = "",

    @SerialName("is_theme")
    val isTheme: Boolean = false
)