package com.example.tmdb.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.tmdb.feature.home.data.genre.GenreDao
import com.example.tmdb.feature.home.data.genre.GenreEntity
import com.example.tmdb.feature.home.data.nowplaying.NowPlayingDao
import com.example.tmdb.feature.home.data.nowplaying.NowPlayingEntity
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieDao
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieEntity
import com.example.tmdb.feature.home.data.topmovies.TopMovieDao
import com.example.tmdb.feature.home.data.topmovies.TopMovieEntity

@TypeConverters(DatabaseTypeConvertor::class)
@Database(
    entities = [PopularMovieEntity::class, GenreEntity::class, TopMovieEntity::class, NowPlayingEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun TopMovieDao(): TopMovieDao
    abstract fun NowPlayingDao(): NowPlayingDao
    abstract fun genreDao(): GenreDao
}
