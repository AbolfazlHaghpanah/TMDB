package com.example.tmdb.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.feature.home.data.genre.dao.GenreDao
import com.example.tmdb.feature.home.data.genre.entity.GenreEntity
import com.example.tmdb.feature.home.data.nowplayingmovie.NowPlayingEntity
import com.example.tmdb.feature.home.data.popularMovie.PopularMovieEntity
import com.example.tmdb.feature.home.data.topmovie.TopMovieEntity
import com.example.tmdb.feature.home.data.popularMovie.relation.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.topmovie.relation.crossref.TopMovieGenreCrossRef

@TypeConverters(DatabaseTypeConvertor::class)
@Database(
    entities = [
        MovieEntity::class,
        PopularMovieEntity::class,
        GenreEntity::class,
        TopMovieEntity::class,
        NowPlayingEntity::class,
        PopularMovieGenreCrossRef::class,
        TopMovieGenreCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
}
