package com.example.tmdb.feature.detail.data.source.local

import com.example.tmdb.core.data.movie.dao.MovieDao
import com.example.tmdb.feature.detail.data.source.local.dao.DetailDao
import com.example.tmdb.feature.detail.data.source.local.relation.DetailMovieWithAllRelations
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val detailDao: DetailDao,
    private val movieDao: MovieDao
) {
    fun observeMovieDetail(detailMovieId: Int): Flow<DetailMovieWithAllRelations> {
        return detailDao.observeMovieDetail(detailMovieId)
    }
}