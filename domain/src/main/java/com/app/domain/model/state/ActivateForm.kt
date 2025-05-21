package com.app.domain.model.state

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Serializable
@Keep
/**
 * 활동 형태 모델
 */
data class ActivateForm(
    val index: String = "",
    val name: String = "오늘은! 출근",
    val description: String = "",
    val assets: String = "",

    /** 활동 종류 **/
    var activateFormResId: Int = 2131165330,

    /** 마커 표시 팝업 **/
    var showMarkerPopup: Boolean = false,

    /**
     * 목표 위치 선택 후, 위도 경도 저장
     */
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,

): ActivityType