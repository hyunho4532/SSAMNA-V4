package com.app.domain.model.state

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
/**
 * 활동 모델
 */
data class Activate(
    val index: String = "",
    val name: String = "",
    val description: String = "",
    val assets: String = "",

    /**
     * Google ID
     */
    val googleId: String = "",

    /** 활동 종류 **/
    var activateResId: Int = 2131165330,
    var activateName: String = "달리기",

    /** 측정 상태 **/
    var activateButtonName: String = "측정하기!",

    /** 만보기 걸음 수 **/
    var prevPedometerCount: Int = 0,
    var pedometerCount: Int = 0,

    /**
     * 현재 러닝 진행 중인지 상태를 보여줌
     */
    var showRunningStatus: Boolean = false,
    var isRunning: Boolean = false,

    /**
     * 화면 상태
     */
    var screenType: String = "",

    /**
     * 이모티콘
     */
    var statusIcon: Int = 2131165372,
    var statusName: String = "매우 좋음",

    /**
     *
     */
    var runningTitle: String = "",

    /**
     * 시간
     */
    var time: Long = 0L

): ActivityType