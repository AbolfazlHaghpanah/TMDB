package com.hooshang.tmdb.core.di

import com.hooshang.tmdb.core.data.AppDatabase
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tmdb-db"
        )
            .setQueryCallback(object : RoomDatabase.QueryCallback {
                override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                    Log.d("databse-tag", sqlQuery)
                    Log.d("databse-tag", bindArgs.toString())
                    Log.d("databse-tag", "------------------+")
                }
            }, Executors.newSingleThreadExecutor())
            .build()
    }
}