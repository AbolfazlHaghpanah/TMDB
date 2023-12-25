package com.hooshang.tmdb.feature.search.data.repository

import com.hooshang.tmdb.core.data.model.local.GenreEntity
import com.hooshang.tmdb.feature.search.data.datasource.localdatasource.SearchLocalDataSource
import com.hooshang.tmdb.feature.search.data.datasource.remotedatasource.SearchRemoteDataSource
import com.hooshang.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.hooshang.tmdb.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchLocalDataSource: SearchLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun searchMovie(query: String): List<SearchMovieWithGenreDomainModel> {
        return searchRemoteDataSource.searchMovie(query).results
            .map { it.toDomainModel() }
            .map {
                SearchMovieWithGenreDomainModel(
                    movieDomainModel = it.first,
                    genres = getGenres(it.second)
                )
            }
    }

    private suspend fun getGenres(ids: List<Int>): String {
        val genres = mutableListOf<GenreEntity>()

        searchLocalDataSource
            .observeGenres()
            .first()
            .onEach { genre ->
                if (ids.contains(genre.genreId)) genres.add(genre)
            }

        return genres.joinToString(separator = "|") { it.genreName }
    }
}