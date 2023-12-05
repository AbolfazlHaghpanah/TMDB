package com.example.tmdb.feature.search.data.repository

import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.feature.search.data.local.localdatasource.SearchLocalDataSource
import com.example.tmdb.feature.search.data.remote.remotedatasource.SearchRemoteDataSource
import com.example.tmdb.feature.search.domain.model.SearchMovieWithGenreDomainModel
import com.example.tmdb.feature.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchLocalDataSource: SearchLocalDataSource,
    private val searchRemoteDataSource: SearchRemoteDataSource
) : SearchRepository {

    override suspend fun searchMovie(value: String): List<SearchMovieWithGenreDomainModel> {
        return searchRemoteDataSource.searchMovie(value).results
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
            .getGenres()
            .first()
            .onEach { genre ->
                if (ids.contains(genre.genreId)) genres.add(genre)
            }

        return genres.joinToString(separator = "|") { it.genreName }
    }
}