package com.app.domain.repository.sensor

import android.hardware.SensorEventListener

interface SensorRepository {
    fun updateNotification(stepCount: Int)
    fun sensorListener(setStepCount: (Int) -> Unit): SensorEventListener
}