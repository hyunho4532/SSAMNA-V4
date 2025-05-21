package com.app.data.modules

import com.app.data.repository.challenge.ChallengeRepositoryImpl
import com.app.domain.repository.challenge.ChallengeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ChallengeModule {
    @Binds
    abstract fun bindChallengeRepository(
        impl: ChallengeRepositoryImpl
    ): ChallengeRepository
}