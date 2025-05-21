package com.app.data.manager

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Build
import androidx.core.app.NotificationCompat
import com.app.data.R
import com.app.data.service.SensorService
import com.app.domain.manager.SensorServiceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SensorManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SensorServiceManager {
    override fun startSensorService(context: Context) {
        val intent = Intent(context, SensorService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    override fun stopSensorService(context: Context) {
        val intent = Intent(context, SensorService::class.java)
        context.stopService(intent)
    }

    override fun sensorListener(stepCount: Int, setStepCount: (Int) -> Unit): SensorEventListener {
        var newStepCount = stepCount

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    newStepCount++

                    updateNotification(newStepCount)

                    setStepCount(newStepCount)
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        return listener
    }

    override fun updateNotification(stepCount: Int) {
        val notification: Notification = NotificationCompat.Builder(context, "step_counter_channel")
            .setSmallIcon(R.drawable.baseline_persons_run_24)
            .setContentTitle("걸음을 유지하세요!!")
            .setContentText("현재 걸음 수: $stepCount")
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}