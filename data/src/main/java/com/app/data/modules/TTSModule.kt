package com.app.data.modules

import com.app.data.repository.tts.TTSRepositoryImpl
import com.app.domain.repository.TTSRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TTSModule {
    @Binds
    abstract fun bindTTSRepository(
        impl: TTSRepositoryImpl
    ): TTSRepository
}