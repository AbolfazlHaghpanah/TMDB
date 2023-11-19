package com.example.tmdb.core.data.moviedata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tmdb.feature.favorite.data.FavoriteMovieEntity
import com.example.tmdb.feature.favorite.data.relation.FavoriteMovieGenreCrossRef
import com.example.tmdb.feature.home.data.nowplayingmovie.NowPlayingEntity
import com.example.tmdb.feature.home.data.nowplayingmovie.relation.NowPlayingWithMovie
import com.example.tmdb.feature.home.data.popularMovie.PopularMovieEntity
import com.example.tmdb.feature.home.data.popularMovie.relation.PopularMovieAndGenreWithMovie
import com.example.tmdb.feature.home.data.popularMovie.relation.PopularMovieGenreCrossRef
import com.example.tmdb.feature.home.data.topmovie.TopMovieEntity
import com.example.tmdb.feature.home.data.topmovie.relation.TopMovieAndGenreWithMovie
import com.example.tmdb.feature.home.data.topmovie.relation.crossref.TopMovieGenreCrossRef
import com.example.tmdb.feature.home.network.json.MovieResult
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNowPlayingMovie(movie: NowPlayingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMovie(movie: PopularMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(movieEntity: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMovie(movie: TopMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMoviesGenre(genre: TopMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovieGenre(genre: FavoriteMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMoviesGenre(genre: PopularMovieGenreCrossRef)

    @Query("SELECT * FROM POPULAR_MOVIES")
    fun observePopularMovie(): Flow<List<PopularMovieAndGenreWithMovie>>

    @Query("SELECT * FROM TOP_MOVIE")
    fun observeTopMovie(): Flow<List<TopMovieAndGenreWithMovie>>

    @Query("SELECT * FROM NOW_PLAYING")
    fun observeNowPlayingMovie(): Flow<List<NowPlayingWithMovie>>

    @Transaction
    suspend fun addNowPlayingMovie(
        nowPlaying: NowPlayingEntity,
        movie: MovieEntity
    ) {
        addNowPlayingMovie(nowPlaying)
        addMovie(movie)
    }

    @Transaction
    suspend fun addPopularMovie(
        movie : MovieResult
    ) {
        addPopularMovie(movie.toPopularMovieEntity())
        addMovie(movie.toMovieEntity())
        movie.genreIds.forEach {
            addPopularMoviesGenre(
                PopularMovieGenreCrossRef(
                    movieId = movie.id,
                    genreId = it
                )
            )
        }
    }

    @Transaction
    suspend fun addTopMovie(
        movie: MovieResult
    ) {
        addTopMovie(movie.toTopPlayingEntity())
        addMovie(movie.toMovieEntity())
        movie.genreIds.forEach {
            addTopMoviesGenre(
                TopMovieGenreCrossRef(
                    movieId = movie.id,
                    genreId = it
                )
            )
        }
    }
}