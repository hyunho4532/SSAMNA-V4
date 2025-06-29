package com.app.domain.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO (
    @SerialName("user_id")
    val userId: String = "",

    @SerialName("email")
    val email: String = "",

    @SerialName("name")
    val name: String = "",

    @SerialName("age")
    val age: Int = 0,

    @SerialName("recentExerciseCheck")
    val recentExerciseCheck: String = "",

    @SerialName("recentExerciseName")
    val recentExerciseName: String = "",

    @SerialName("recentWalkingCheck")
    val recentWalkingCheck: String = "",

    @SerialName("recentWalkingOfWeek")
    val recentWalkingOfWeek: String = "",

    @SerialName("recentWalkingOfTime")
    val recentWalkingOfTime: String = "",

    @SerialName("targetPeriod")
    val targetPeriod: String = "",

    @SerialName("profile_url")
    val profileUrl: String? = ""
)