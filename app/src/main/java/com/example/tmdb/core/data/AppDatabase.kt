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
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.feature.favorite.data.FavoriteMovieEntity
import com.example.tmdb.feature.favorite.data.FavoriteMovieDao
import com.example.tmdb.feature.favorite.data.relation.FavoriteMovieGenreCrossRef
import com.example.tmdb.feature.home.data.nowplayingmovie.NowPlayingEntity
import com.example.tmdb.feature.home.data.popularMovie.PopularMovieEntity
import com.example.tmdb.feature.home.data.topmovie.TopMovieEntity
import com.example.tmdb.feature.home.data.popularMovie.relation.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.topmovie.relation.crossref.TopMovieGenreCrossRef

@TypeConverters(DatabaseTypeConvertor::class, ListStringToStringConverter::class)
@Database(
    entities = [
        MovieEntity::class,
        PopularMovieEntity::class,
        GenreEntity::class,
        TopMovieEntity::class,
        NowPlayingEntity::class,
        FavoriteMovieEntity::class,
        FavoriteMovieGenreCrossRef::class,
        PopularMovieGenreCrossRef::class,
        TopMovieGenreCrossRef::class
//        PopularMovieEntity::class, GenreEntity::class, TopMovieEntity::class,
//        NowPlayingEntity::class, CreditEntity::class, MovieEntity::class, DetailEntity::class,
//        DetailMovieWithSimilarMoviesCrossRef::class, DetailMovieWithCreditCrossRef::class,
//        DetailMovieWithGenreCrossRef::class, Genre::class, MovieWithGenreCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun CreditDao(): CreditDao
    abstract fun MovieDao(): MovieDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
