package com.example.tmdb.feature.detail.data.detail.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tmdb.feature.favorite.data.entity.FavoriteMovieEntity
import com.example.tmdb.feature.detail.data.credit.entity.CreditEntity
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.detail.entity.DetailEntity
import com.example.tmdb.feature.detail.data.relation.DetailMovieWithAllRelations
import com.example.tmdb.feature.favorite.data.relation.FavoriteMovieGenreCrossRef
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
    suspend fun addFavoriteMovieGenre(genre: FavoriteMovieGenreCrossRef)

    @Delete
    suspend fun deleteFavoriteMovieGenre(favoriteMovieGenreCrossRef: FavoriteMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithCreditCrossRef(detailMovieWithCreditCrossRef: DetailMovieWithCreditCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithGenreCrossRef(detailMovieWithGenreCrossRef: DetailMovieWithGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithSimilarMoviesCrossRef(detailMovieWithSimilarMoviesCrossRef: DetailMovieWithSimilarMoviesCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieWithGenreCrossRef(movieWithGenre: MovieWithGenreCrossRef)

    //    other entities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCredits(credit: List<CreditEntity>)

    @Delete
    suspend fun deleteFavorite(movieEntity: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity)

}