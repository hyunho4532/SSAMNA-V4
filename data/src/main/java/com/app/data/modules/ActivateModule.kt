package com.app.data.modules

import com.app.data.repository.activate.ActivateRepositoryImpl
import com.app.domain.repository.activate.ActivateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ActivateModule {
    @Binds
    abstract fun bindActivateRepository(
        impl: ActivateRepositoryImpl
    ): ActivateRepository
}