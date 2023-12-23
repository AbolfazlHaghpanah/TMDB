package com.hooshang.tmdb.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchedModule {
    @Provides
    fun provideCoroutineContext() : CoroutineDispatcher = Dispatchers.IO
}