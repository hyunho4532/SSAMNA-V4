package com.app.data.modules

import com.app.data.repository.state.StateRepositoryImpl
import com.app.domain.repository.StateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StateModule {
    @Binds
    abstract fun bindStateRepository(
        impl: StateRepositoryImpl
    ): StateRepository
}