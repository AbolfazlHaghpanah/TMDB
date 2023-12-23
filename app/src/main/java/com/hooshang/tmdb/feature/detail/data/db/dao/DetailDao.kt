package com.hooshang.tmdb.feature.detail.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hooshang.tmdb.feature.detail.data.db.entity.CreditEntity
import com.hooshang.tmdb.feature.detail.data.db.entity.DetailEntity
import com.hooshang.tmdb.feature.detail.data.db.relation.DetailMovieWithMovieAndGenre
import com.hooshang.tmdb.feature.detail.data.db.relation.SimilarMovieWithGenre
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithCreditCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithGenreCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.hooshang.tmdb.feature.detail.data.db.relation.crossrefrence.MovieWithGenreCrossRef
import com.hooshang.tmdb.feature.favorite.data.model.local.entity.FavoriteMovieEntity
import com.hooshang.tmdb.feature.favorite.data.model.local.relation.FavoriteMovieGenreCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailDao {

    @Query("select * from detail_movies where detailMovieId = :detailMovieId")
    fun observeMovieDetail(detailMovieId: Int): Flow<DetailMovieWithMovieAndGenre>

    @Query("select * from credits where creditId in (:ids)")
    fun observeCredits(ids: List<Int>): Flow<List<CreditEntity>>

    @Query(
        """
            select credits.* from credits
            left join detailmoviewithcreditcrossref on credits.creditId = detailmoviewithcreditcrossref.creditId
            where detailmoviewithcreditcrossref.detailMovieId = :id
        """
    )
    fun observeCredits(id: Int): Flow<List<CreditEntity>>

    @Query(
        """
            select movies.* from movies
            left join detailmoviewithsimilarmoviescrossref on movies.id = detailmoviewithsimilarmoviescrossref.id 
            where detailmoviewithsimilarmoviescrossref.detailMovieId = :id
        """
    )
    fun observeSimilarMovie(id: Int): Flow<List<SimilarMovieWithGenre>>

    @Query("select exists (select 1 from FAVORITE_MOVIE where movieId =:id)")
    fun isExistInFavorite(id: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(detailEntity: DetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMoviesWithGenres(detailMovieWithGenreCrossRef: List<DetailMovieWithGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCredits(credit: List<CreditEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMoviesWithCredits(detailMovieWithCreditCrossRef: List<DetailMovieWithCreditCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetailMoviesWithSimilarMovies(detailMovieWithSimilarMoviesCrossRef: List<DetailMovieWithSimilarMoviesCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoviesWithGenres(movieWithGenre: List<MovieWithGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovieGenre(genres: List<FavoriteMovieGenreCrossRef>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavoriteMovieGenre(favoriteMovieGenreCrossRef: FavoriteMovieGenreCrossRef)

    @Delete
    suspend fun deleteFavorite(movieEntity: FavoriteMovieEntity)
}