package com.example.tmdb.feature.detail.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

//TODO Unused
@Dao
interface MovieDao {
    @Transaction
    @Query("SELECT * from detail_movies where :detailMovieId")
    fun getDetailMovieWithAllRelations(detailMovieId: Int): Flow<DetailMovieWithAllRelations>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovie(detailEntity: DetailEntity)
}