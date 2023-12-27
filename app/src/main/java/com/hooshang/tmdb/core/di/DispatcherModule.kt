package com.hooshang.tmdb.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Provides
    fun provideDispatcher(): CoroutineContext = Dispatchers.IO
}