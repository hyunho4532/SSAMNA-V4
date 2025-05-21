package com.app.data.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.app.data.R
import com.app.data.repository.sensor.SensorRepositoryImpl


class SensorService : Service(), SensorServiceImpl {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensorRepository: SensorRepositoryImpl

    override fun onCreate() {
        super.onCreate()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorRepository = SensorRepositoryImpl(this)

        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor != null) {
            sensorManager.registerListener(sensorRepository, stepSensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            stopSelf()
        }

        startForegroundService()
    }

    private fun startForegroundService() {
        val channelId = "step_counter_channel"
        val channelName = "Step Counter"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_persons_run_24)
            .setContentTitle("걸음을 유지하세요!!")
            .setContentText("현재 걸음 수: 0")
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(sensorRepository)
    }

    override fun startService() {

    }
}