package com.example.tmdb.feature.home.data.nowplayingmovie.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.tmdb.core.data.moviedata.MovieEntity
import com.example.tmdb.feature.home.data.common.MovieDatabaseWrapper
import com.example.tmdb.feature.home.data.common.MovieWithGenreDatabaseWrapper
import com.example.tmdb.feature.home.data.nowplayingmovie.NowPlayingEntity

data class NowPlayingWithMovie(
    @Embedded val nowPlayingMovie: NowPlayingEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "id"
    )
    val movie: MovieEntity,
) {
    fun toMovieDataWrapper(): MovieWithGenreDatabaseWrapper {
        return MovieWithGenreDatabaseWrapper(
            genres = listOf(),
            movie = MovieDatabaseWrapper(
                movieId = movie.id,
                title = movie.title,
                posterPath = movie.posterPath,
                voteAverage = movie.voteAverage,
                backdropPath = movie.backdropPath,
                releaseDate = nowPlayingMovie.releaseDate
            )
        )
    }
}