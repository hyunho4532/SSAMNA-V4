package com.app.data.modules

import android.content.Context
import com.app.data.manager.tts.TTSManager
import com.app.data.manager.tts.TTSManagerImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TTSManagerModule {

    @Binds
    @Singleton
    abstract fun bindTTSManager(impl: TTSManagerImpl): TTSManager

    companion object {
        @Provides
        @Singleton
        fun provideTTSManagerImpl(
            @ApplicationContext context: Context
        ): TTSManagerImpl {
            return TTSManagerImpl(context)
        }
    }
}
