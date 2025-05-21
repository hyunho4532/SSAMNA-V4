package com.app.domain.model.location

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    /** 위도, 경도, 고도, km **/
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var altitude: Double = 0.0,
    var km: Double = 0.0,
)