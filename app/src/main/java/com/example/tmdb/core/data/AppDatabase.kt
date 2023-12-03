package com.example.tmdb.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tmdb.core.data.genre.dao.GenreDao
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.core.data.movie.dao.MovieDao
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.feature.detail.data.converter.ListStringToStringConverter
import com.example.tmdb.feature.detail.data.entity.CreditEntity
import com.example.tmdb.feature.detail.data.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.relation.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.dao.DetailDao
import com.example.tmdb.feature.detail.data.entity.DetailEntity
import com.example.tmdb.feature.favorite.data.dao.FavoriteMovieDao
import com.example.tmdb.feature.favorite.data.entity.FavoriteMovieEntity
import com.example.tmdb.feature.favorite.data.relation.FavoriteMovieGenreCrossRef
import com.example.tmdb.feature.home.data.local.dao.HomeDao
import com.example.tmdb.feature.home.data.local.entity.NowPlayingEntity
import com.example.tmdb.feature.home.data.local.entity.PopularMovieEntity
import com.example.tmdb.feature.home.data.local.relation.crossref.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.local.entity.TopMovieEntity
import com.example.tmdb.feature.home.data.local.relation.crossref.TopMovieGenreCrossRef

@TypeConverters(ListStringToStringConverter::class)
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
    abstract fun HomeDao(): HomeDao
}
