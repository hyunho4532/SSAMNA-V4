package com.app.data.repository.sensor

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.core.app.NotificationCompat
import com.app.data.R
import com.app.domain.repository.SensorRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SensorRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): SensorRepository, SensorEventListener {

    private var initialStepCount: Int? = null
    private var currentStepCount: Int = 0

    override fun updateNotification(stepCount: Int) {
        val notification: Notification = NotificationCompat.Builder(context, "step_counter_channel")
            .setSmallIcon(R.drawable.baseline_persons_run_24)
            .setContentTitle("걸음을 유지하세요!!")
            .setContentText("현재 걸음 수: $stepCount")
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    override fun sensorListener(setStepCount: (Int) -> Unit): SensorEventListener {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    if (initialStepCount == null) {
                        initialStepCount = it.values[0].toInt()
                    }
                    currentStepCount = it.values[0].toInt() - (initialStepCount ?: 0)


                    updateNotification(currentStepCount)
                    setStepCount(currentStepCount)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        return listener
    }

    override fun onSensorChanged(event: SensorEvent?) {

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}