package com.app.data.modules

import com.app.data.repository.sensor.SensorRepositoryImpl
import com.app.domain.repository.SensorRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SensorModule {
    @Binds
    abstract fun bindSensorRepository(
        impl: SensorRepositoryImpl
    ): SensorRepository
}