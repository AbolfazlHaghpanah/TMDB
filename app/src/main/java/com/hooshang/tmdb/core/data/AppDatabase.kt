package com.hooshang.tmdb.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.core.data.source.local.MovieDao
import com.hooshang.tmdb.core.data.model.local.MovieEntity
import com.hooshang.tmdb.feature.detail.data.db.converter.ListStringToStringConverter
import com.hooshang.tmdb.feature.detail.data.db.dao.DetailDao
import com.hooshang.tmdb.feature.detail.data.db.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.favorite.data.source.local.dao.FavoriteMovieDao
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.db.HomeDao
import com.hooshang.tmdb.feature.home.data.db.entity.NowPlayingEntity
import com.hooshang.tmdb.feature.home.data.db.entity.PopularMovieEntity
import com.hooshang.tmdb.feature.home.data.db.entity.TopMovieEntity
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.PopularMovieGenreCrossRef
import com.hooshang.tmdb.feature.home.data.db.relation.crossref.TopMovieGenreCrossRef
import com.hooshang.tmdb.feature.search.data.source.local.dao.SearchDao

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
abstract class AppDatabase() : RoomDatabase() {

    abstract fun MovieDao(): MovieDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun DetailDao(): DetailDao
    abstract fun HomeDao(): HomeDao
    abstract fun SearchDao(): SearchDao
}