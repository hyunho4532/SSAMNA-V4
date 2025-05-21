package com.app.data.modules

import com.app.data.repository.common.CodeRepositoryImpl
import com.app.domain.repository.common.CodeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CodeModule {
    @Binds
    abstract fun bindCodeRepository(
        impl: CodeRepositoryImpl
    ): CodeRepository
}