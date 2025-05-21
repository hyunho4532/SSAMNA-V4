package com.app.data.service

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.app.data.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


@SuppressLint("SpecifyJobSchedulerIdRange")
class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate() {
        super.onCreate()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object: LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    sendLocationBroadcast(location.latitude, location.longitude, location.altitude)
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun sendLocationBroadcast(latitude: Double, longitude: Double, altitude: Double) {
        val intent = Intent("com.ssamna.LOCATION_UPDATE").apply {
            putExtra("latitude", latitude)
            putExtra("longitude", longitude)
            putExtra("altitude", altitude)
        }
        sendBroadcast(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForegroundService()
        requestLocationUpdates()
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "location_service_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "위치 서비스",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("위치 서비스 실행 중")
            .setContentText("5초마다 위치를 추적합니다")
            .setSmallIcon(R.drawable.baseline_persons_run_24)
            .build()

        startForeground(1, notification)
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}