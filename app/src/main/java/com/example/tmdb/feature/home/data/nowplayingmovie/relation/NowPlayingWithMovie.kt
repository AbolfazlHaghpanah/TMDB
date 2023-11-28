package com.example.tmdb.feature.home.data.nowplayingmovie.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tmdb.core.data.moviedata.Entity.MovieEntity
import com.example.tmdb.core.utils.MovieDatabaseWrapper
import com.example.tmdb.core.utils.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.nowplayingmovie.entity.NowPlayingEntity
import kotlinx.collections.immutable.persistentListOf

data class NowPlayingWithMovie(
    @Embedded val nowPlayingMovie: NowPlayingEntity?,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val movie: MovieEntity?,
) {
    fun toMovieDataWrapper(): MovieWithGenreDatabaseWrapper {
        return MovieWithGenreDatabaseWrapper(
            genres = persistentListOf(),
            movie = MovieDatabaseWrapper(
                movieId = movie?.id ?: 1,
                title = movie?.title ?: "",
                posterPath = movie?.posterPath ?: "",
                voteAverage = movie?.voteAverage ?: 0.0,
                releaseDate = nowPlayingMovie?.releaseDate ?: ""
            )
        )
    }
}