package com.app.data.modules

import com.app.data.repository.crew.CrewRepositoryImpl
import com.app.domain.repository.CrewRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CrewModule {
    @Binds
    abstract fun bindCrewRepository(
        impl: CrewRepositoryImpl
    ): CrewRepository
}