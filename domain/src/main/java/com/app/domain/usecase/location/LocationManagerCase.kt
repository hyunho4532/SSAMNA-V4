package com.app.domain.usecase.location

import android.content.Context
import com.app.domain.manager.LocationServiceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationManagerCase @Inject constructor(
    private val locationServiceManager: LocationServiceManager
) {
    fun startService(context: Context) {
        locationServiceManager.startLocationService(context)
    }

    fun stopService(context: Context) {
        locationServiceManager.stopLocationService(context)
    }

    suspend fun getCurrentLocation(): Flow<Pair<List<Double>, Double>> {
        return locationServiceManager.locationFlow()
    }
}