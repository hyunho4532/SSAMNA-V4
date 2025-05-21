package com.app.data.modules

import com.app.data.manager.LocationManagerImpl
import com.app.domain.manager.LocationServiceManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationManagerModule {
    @Binds
    abstract fun bindLocationManager(
        impl: LocationManagerImpl
    ): LocationServiceManager
}