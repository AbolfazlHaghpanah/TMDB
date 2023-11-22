package com.example.tmdb.core.di

import com.example.tmdb.core.utils.SnackBarMessage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TMDBSnackBar {

    @Singleton
    @Provides
    fun provideSnackBar(): SnackBarMessage {
        return SnackBarMessage(Channel())
    }
}