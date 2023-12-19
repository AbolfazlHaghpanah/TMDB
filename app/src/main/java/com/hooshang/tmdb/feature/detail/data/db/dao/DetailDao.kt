package com.hooshang.tmdb.feature.detail.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hooshang.tmdb.feature.detail.data.db.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.DetailMovieWithAllRelations
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailDao {

    //   movie detail
    @Query("select * from detail_movies where detailMovieId = :detailMovieId")
    fun observeMovieDetail(detailMovieId: Int): Flow<DetailMovieWithAllRelations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDetail(detailEntity: DetailEntity)

    //    cross references
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovieGenre(genre: FavoriteMovieGenreCrossRef)

    @Delete
    suspend fun deleteFavoriteMovieGenre(favoriteMovieGenreCrossRef: FavoriteMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMoviesWithCredits(detailMovieWithCreditCrossRef: List<DetailMovieWithCreditCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMoviesWithGenres(detailMovieWithGenreCrossRef: List<DetailMovieWithGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMoviesWithSimilarMovies(detailMovieWithSimilarMoviesCrossRef: List<DetailMovieWithSimilarMoviesCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesWithGenres(movieWithGenre: List<MovieWithGenreCrossRef>)

    //    other entities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCredits(credit: List<CreditEntity>)

    @Delete
    suspend fun deleteFavorite(movieEntity: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity)
}