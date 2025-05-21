package com.app.data.manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.data.service.LocationService
import com.app.domain.manager.LocationServiceManager
import com.app.domain.model.calcul.FormatImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationManagerImpl @Inject constructor(
    @ApplicationContext val context: Context
) : LocationServiceManager {

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var altitude: Double = 0.0

    private val locationReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.ssamna.LOCATION_UPDATE") {
                latitude = intent.getDoubleExtra("latitude", 0.0)
                longitude = intent.getDoubleExtra("longitude", 0.0)
                altitude = intent.getDoubleExtra("altitude", 0.0)
            }
        }
    }

    init {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(locationReceiver)
    }

    override fun startLocationService(context: Context) {
        val filter = IntentFilter("com.ssamna.LOCATION_UPDATE")
        val intent = Intent(context, LocationService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(locationReceiver, filter, Context.RECEIVER_EXPORTED)
            }
        } else {
            context.startService(intent)
        }
    }

    override fun stopLocationService(context: Context) {
        val intent = Intent(context, LocationService::class.java)
        context.stopService(intent)
        LocalBroadcastManager.getInstance(context).unregisterReceiver(locationReceiver)
    }

    override fun getLatitude(): Double = latitude

    override fun getLongitude(): Double = longitude

    override fun getAltitude(): Double = altitude

    override suspend fun locationFlow(): Flow<Pair<List<Double>, Double>> = flow {

        val sharedPreferences: SharedPreferences = context.getSharedPreferences("sensor_prefs", Context.MODE_PRIVATE)

        while (true) {
            val pedometerCount = sharedPreferences.getInt("pedometerCount", 0) // 적절한 키를 사용하세요.

            val distanceKm = FormatImpl().calculateDistanceToKm(pedometerCount)

            emit(Pair(listOf(getLatitude(), getLongitude(), getAltitude()), distanceKm))
            delay(5000)
        }
    }
}