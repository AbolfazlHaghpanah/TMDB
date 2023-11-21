package com.example.tmdb.feature.detail.data.detail

import androidx.room.Dao
import androidx.room.Query
import com.example.tmdb.feature.detail.data.relation.DetailMovieWithAllRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailDao {
    @Query("select * from detail_movies where detailMovieId = :detailMovieId")
    fun observeMovieDetail(detailMovieId: Int): Flow<DetailMovieWithAllRelations>


}