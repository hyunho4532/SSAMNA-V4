package com.app.domain.model.user

import kotlinx.serialization.Serializable

/**
 * 사용자에 대한 정보를 담는 데이터 클래스
 */
@Serializable
data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",

    val age: Float = 0f,
    val recentExerciseCheck: String = "네",
    var recentExerciseName: String = "",

    val recentWalkingCheck: String = "네",
    val recentWalkingOfWeek: String = "",
    val recentWalkingOfTime: String = "",

    val targetPeriod: String = "네",
    val isSmartWatch: String = "네"
)