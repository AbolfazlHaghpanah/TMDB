package com.hooshang.tmdb.feature.detail.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hooshang.tmdb.feature.detail.data.model.local.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.model.local.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.model.local.relation.DetailMovieWithAllRelations
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.model.local.relation.crossrefrence.MovieWithGenreCrossRef
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
    suspend fun addFavoriteMovieGenre(genre: FavoriteMovieGenreCrossRef)

    @Delete
    suspend fun deleteFavoriteMovieGenre(favoriteMovieGenreCrossRef: FavoriteMovieGenreCrossRef)

    //    TODO rename for example => addDetailMovieWithCreditCrossReferences for all list insertions and rename parameter
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithCreditCrossRef(detailMovieWithCreditCrossRef: List<DetailMovieWithCreditCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithGenreCrossRef(detailMovieWithGenreCrossRef: List<DetailMovieWithGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithSimilarMoviesCrossRef(detailMovieWithSimilarMoviesCrossRef: List<DetailMovieWithSimilarMoviesCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieWithGenreCrossRef(movieWithGenre: List<MovieWithGenreCrossRef>)

    //    other entities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCredits(credit: List<CreditEntity>)

    @Delete
    suspend fun deleteFavorite(movieEntity: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity)
}