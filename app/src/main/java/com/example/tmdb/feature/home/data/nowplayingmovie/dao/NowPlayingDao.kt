package com.example.tmdb.feature.home.data.nowplayingmovie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.tmdb.feature.home.data.nowplayingmovie.entity.NowPlayingEntity

@Dao
interface NowPlayingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNowPlayingMovie(movie: NowPlayingEntity)
}