package com.app.domain.usecase.sensor

import com.app.domain.repository.SensorRepository
import javax.inject.Inject

class SensorCase @Inject constructor(
    private val sensorRepository: SensorRepository
) {

    private var stepCount: Int = 0

    fun invoke() {
    }
}