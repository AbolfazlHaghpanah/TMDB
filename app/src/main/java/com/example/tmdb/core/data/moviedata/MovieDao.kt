package com.example.tmdb.core.data.moviedata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tmdb.core.data.genre.entity.GenreEntity
import com.example.tmdb.feature.detail.data.credit.CreditEntity
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithCreditCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.DetailMovieWithSimilarMoviesCrossRef
import com.example.tmdb.feature.detail.data.crossrefrence.MovieWithGenreCrossRef
import com.example.tmdb.feature.detail.data.detail.DetailEntity
import com.example.tmdb.feature.detail.network.json.MovieDetail
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

    @Delete
    suspend fun deleteFavorite(movieEntity: FavoriteMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMovie(movie: TopMovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTopMoviesGenre(genre: TopMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteMovieGenre(genre: FavoriteMovieGenreCrossRef)

    @Delete
    suspend fun deleteFavoriteMovieGenre(favoriteMovieGenreCrossRef: FavoriteMovieGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPopularMoviesGenre(genre: PopularMovieGenreCrossRef)

    @Query("SELECT * FROM POPULAR_MOVIES")
    fun observePopularMovie(): Flow<List<PopularMovieAndGenreWithMovie>>

    @Query("SELECT * FROM TOP_MOVIE")
    fun observeTopMovie(): Flow<List<TopMovieAndGenreWithMovie>>

    @Query("SELECT * FROM NOW_PLAYING")
    fun observeNowPlayingMovie(): Flow<List<NowPlayingWithMovie>>

    @Transaction
    suspend fun addToFavorite(
        movie: FavoriteMovieEntity,
        genres: List<GenreEntity>
    ) {
        genres.forEach {
            addFavoriteMovieGenre(
                FavoriteMovieGenreCrossRef(
                    movie.movieId,
                    it.genreId
                )
            )
        }
        addToFavorite(movie)
    }

    @Transaction
    suspend fun removeFavorite(
        movie: FavoriteMovieEntity,
        genres: List<GenreEntity>
    ) {
        genres.forEach {
            deleteFavoriteMovieGenre(
                FavoriteMovieGenreCrossRef(
                    movieId = movie.movieId,
                    genreId = it.genreId
                )
            )
        }
        deleteFavorite(movie)
    }

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
        movie: MovieResult
    ) {
        addPopularMovie(movie.toPopularMovieEntity())
        addMovie(movie.toMovieEntity())
        movie.genreIds?.forEach {
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
        movie.genreIds?.forEach {
            addTopMoviesGenre(
                TopMovieGenreCrossRef(
                    movieId = movie.id,
                    genreId = it
                )
            )
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDetail(detailEntity: DetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithCreditCrossRef(detailMovieWithCreditCrossRef: DetailMovieWithCreditCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithGenreCrossRef(detailMovieWithGenreCrossRef: DetailMovieWithGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDetailMovieWithSimilarMoviesCrossRef(detailMovieWithSimilarMoviesCrossRef: DetailMovieWithSimilarMoviesCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieWithGenreCrossRef(movieWithGenre: MovieWithGenreCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCredits(credit: List<CreditEntity>)

    @Transaction
    suspend fun addMovieDetail(movieDetail: MovieDetail) {
        addMovie(
            MovieEntity(
                id = movieDetail.id,
                posterPath = movieDetail.posterPath,
                voteAverage = movieDetail.voteAverage.toDouble(),
                backdropPath = "",
                title = movieDetail.title
            )
        )
        addDetail(movieDetail.toDetailEntity())
        addCredits(movieDetail.toCreditsEntity())
        movieDetail.credits.cast.forEach {
            addDetailMovieWithCreditCrossRef(
                DetailMovieWithCreditCrossRef(
                    detailMovieId = movieDetail.id,
                    creditId = it.id
                )
            )
        }
        movieDetail.credits.crew.forEach {
            addDetailMovieWithCreditCrossRef(
                DetailMovieWithCreditCrossRef(
                    detailMovieId = movieDetail.id,
                    creditId = it.id
                )
            )
        }
        movieDetail.genres.forEach {
            addDetailMovieWithGenreCrossRef(
                DetailMovieWithGenreCrossRef(
                    detailMovieId = movieDetail.id,
                    genreId = it.id
                )
            )
        }
        movieDetail.similar.results.forEach {
            addDetailMovieWithSimilarMoviesCrossRef(
                DetailMovieWithSimilarMoviesCrossRef(
                    detailMovieId = movieDetail.id,
                    id = it.id
                )
            )
            addMovie(
                MovieEntity(
                    id = it.id,
                    title = it.title,
                    backdropPath = "",
                    voteAverage = it.voteAverage.toDouble(),
                    posterPath = it.posterPath ?: ""
                )
            )
        }
        movieDetail.similar.results.forEach { similarMovieResult ->
            similarMovieResult.genreIds.forEach { genreId ->
                addMovieWithGenreCrossRef(
                    MovieWithGenreCrossRef(
                        id = similarMovieResult.id,
                        genreId = genreId
                    )
                )
            }
        }
    }
}