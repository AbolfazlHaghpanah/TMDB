package com.example.tmdb.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.moviedata.MovieDao
import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.feature.detail.data.converter.ListStringToStringConverter
import com.example.tmdb.feature.detail.data.credit.CreditEntity
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.detail.DetailDao
import com.example.tmdb.feature.detail.data.detail.DetailEntity
import com.example.tmdb.feature.favorite.data.FavoriteMovieDao
import com.example.tmdb.feature.favorite.data.FavoriteMovieEntity
import com.example.tmdb.feature.favorite.data.relation.FavoriteMovieGenreCrossRef
import com.example.tmdb.feature.home.data.nowplayingmovie.NowPlayingEntity
import com.example.tmdb.feature.home.data.popularMovie.PopularMovieEntity
import com.example.tmdb.feature.home.data.popularMovie.relation.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.topmovie.TopMovieEntity
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
        TopMovieGenreCrossRef::class,
        CreditEntity::class,
        DetailEntity::class,
        DetailMovieWithSimilarMoviesCrossRef::class,
        DetailMovieWithCreditCrossRef::class,
        DetailMovieWithGenreCrossRef::class,
        MovieWithGenreCrossRef::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun genreDao(): GenreDao
    abstract fun MovieDao(): MovieDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun DetailDao(): DetailDao
}
