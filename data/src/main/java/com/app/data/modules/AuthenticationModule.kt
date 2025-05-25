package com.app.data.modules

import com.app.data.repository.auth.AuthenticationRepositoryImpl
import com.app.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthenticationModule {
    @Binds
    abstract fun bindAuthenticationRepository(
        impl: AuthenticationRepositoryImpl
    ): AuthenticationRepository
}