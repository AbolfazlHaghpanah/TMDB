package com.example.tmdb.feature.home.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tmdb.core.data.movie.entity.MovieEntity
import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.local.entity.NowPlayingEntity
import com.example.tmdb.feature.home.ui.model.HomeMovieUiModel
import kotlinx.collections.immutable.persistentListOf

data class NowPlayingWithMovie(
    @Embedded val nowPlayingMovie: NowPlayingEntity?,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val movie: MovieEntity?,
) {
    fun toUiModel(): HomeMovieUiModel {
        return HomeMovieUiModel(
            genres = "",
            movieId = movie?.id ?: 1,
            title = movie?.title ?: "",
            posterPath = movie?.posterPath ?: "",
            voteAverage = movie?.voteAverage ?: 0.0,
            releaseDate = nowPlayingMovie?.releaseDate ?: "",
            backdropPath = movie?.backdropPath ?: ""
        )
    }
}