package com.app.data.modules

import com.app.data.repository.showdown.ShowdownRepositoryImpl
import com.app.domain.repository.ShowdownRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ShowdownModule {
    @Binds
    abstract fun bindShowdownRepository(
        impl: ShowdownRepositoryImpl
    ): ShowdownRepository
}