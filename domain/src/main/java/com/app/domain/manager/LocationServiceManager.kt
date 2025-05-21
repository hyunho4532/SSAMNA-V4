package com.app.domain.manager

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface LocationServiceManager {
    fun startLocationService(context: Context)
    fun stopLocationService(context: Context)
    fun getLatitude(): Double
    fun getLongitude(): Double
    fun getAltitude() : Double
    suspend fun locationFlow(): Flow<Pair<List<Double>, Double>>
}