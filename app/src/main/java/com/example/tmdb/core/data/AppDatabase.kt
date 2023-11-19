package com.example.tmdb.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb.feature.detail.data.CreditDao
import com.example.tmdb.feature.detail.data.CreditEntity
import com.example.tmdb.feature.detail.data.DetailEntity
import com.example.tmdb.feature.detail.data.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.Genre
import com.example.tmdb.feature.detail.data.ListStringToStringConverter
import com.example.tmdb.feature.detail.data.MovieDao
import com.example.tmdb.feature.detail.data.MovieEntity
import com.example.tmdb.feature.detail.data.MovieWithGenreCrossRef
import com.example.tmdb.feature.home.data.genre.GenreDao
import com.example.tmdb.feature.home.data.genre.GenreEntity
import com.example.tmdb.feature.home.data.nowplaying.NowPlayingDao
import com.example.tmdb.feature.home.data.nowplaying.NowPlayingEntity
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieDao
import com.example.tmdb.feature.home.data.popularmovies.PopularMovieEntity
import com.example.tmdb.feature.home.data.topmovies.TopMovieDao
import com.example.tmdb.feature.home.data.topmovies.TopMovieEntity

@TypeConverters(DatabaseTypeConvertor::class, ListStringToStringConverter::class)
@Database(
    entities = [
        PopularMovieEntity::class, GenreEntity::class, TopMovieEntity::class,
        NowPlayingEntity::class, CreditEntity::class, MovieEntity::class, DetailEntity::class,
        DetailMovieWithSimilarMoviesCrossRef::class, DetailMovieWithCreditCrossRef::class,
        DetailMovieWithGenreCrossRef::class, Genre::class, MovieWithGenreCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun popularMovieDao(): PopularMovieDao
    abstract fun TopMovieDao(): TopMovieDao
    abstract fun NowPlayingDao(): NowPlayingDao
    abstract fun genreDao(): GenreDao
    abstract fun CreditDao(): CreditDao
    abstract fun MovieDao(): MovieDao
}
