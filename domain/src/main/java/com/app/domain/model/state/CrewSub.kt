package com.app.domain.model.state

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class CrewSub(
    val index: Int = 0,
    val name: String = "",
    val description: String = "",
    val assets: String = "",
    val member: Int = 0
): ActivityType