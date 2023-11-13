package com.example.tmdb.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb.feature.home.data.genre.dao.GenreDao
import com.example.tmdb.feature.home.data.genre.entity.GenreEntity
import com.example.tmdb.feature.home.data.movie.dao.MovieDao
import com.example.tmdb.feature.home.data.movie.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.movie.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.movie.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.relation.crossref.TopMovieGenreCrossRef

@TypeConverters(DatabaseTypeConvertor::class)
@Database(
    entities = [
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
