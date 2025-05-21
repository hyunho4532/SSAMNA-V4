package com.app.data.modules

import com.app.data.repository.json.JsonParsingRepositoryImpl
import com.app.domain.repository.json.JsonParsingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class JsonParsingModule {
    @Binds
    abstract fun bindJsonParsingRepository(
        impl: JsonParsingRepositoryImpl
    ): JsonParsingRepository
}